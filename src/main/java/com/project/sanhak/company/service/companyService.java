package com.project.sanhak.company.service;

import com.project.sanhak.company.dto.companyDTO;
import com.project.sanhak.company.repository.companyRepository;
import com.project.sanhak.domain.company.Company;
import com.project.sanhak.domain.user.Badge;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.mypage.repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class companyService {
    @Value("${api.base.url}")
    private String apiBaseUrl;
    @Autowired
    private companyRepository companyRepository;
    @Autowired
    private BadgeRepository badgeRepository;
    private final WebClient webClient;

    public companyService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<companyDTO> recommandCompany(User user) {
        String url = apiBaseUrl +"/recommendCompanies";
        Map<String, Object> requestData = new HashMap<>();
        List<Badge> badgeList = badgeRepository.findByUBUid(user);
        Set<String> skillList = badgeList.stream()
                .map(badge -> badge.getUBCSid().getCSName())  // Get CSName from CodeSkil
                .collect(Collectors.toSet());  // Use a Set to avoid duplicates
        List<companyDTO> companyDTOList = new ArrayList<>();
        requestData.put("skill", skillList);
        Map<String, Object> response;
        try {
            response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestData)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            System.out.println("Response data: " + response);

            List<Map<String, Object>> companyResponseList = (List<Map<String, Object>>) response.get("companies");

            for (Map<String, Object> companyData : companyResponseList) {
                String comName = companyData.get("company_name").toString();
                String comPosition = companyData.get("result").toString();
                List<String> comSkill =(List<String>) companyData.get("extracted_skills");
                List<Company> companies = companyRepository.findByCOMNameAndCOMPosition(comName, comPosition);

                for (Company company : companies) {
                    companyDTO dto = new companyDTO();
                    dto.setId(company.getCOMId());
                    dto.setName(company.getCOMName());
                    dto.setLocation(company.getCOMPlace());
                    dto.setPosition(company.getCOMPosition());
                    dto.setDescription(company.getCOMDescription());
                    dto.setSkill(comSkill);
                    companyDTOList.add(dto);
                }
            }
            System.out.println("Matching Company DTOs: " + companyDTOList);
        } catch (Exception e) {
            System.out.println("API 호출 오류: " + e);
            throw new RuntimeException("외부 API 호출 실패: " + e.getMessage(), e);
        }
        return null;
    }
}
