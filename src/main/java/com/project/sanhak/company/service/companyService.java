package com.project.sanhak.company.service;

import com.project.sanhak.company.dto.companyDTO;
import com.project.sanhak.company.repository.companyRepository;
import com.project.sanhak.domain.company.Company;
import com.project.sanhak.domain.user.Badge;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.mypage.repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
        String url = apiBaseUrl + "/recommendCompanies";
        Map<String, Object> requestData = new HashMap<>();
        List<Badge> badgeList = badgeRepository.findByUBUid(user);
        List<String> skillList = badgeList.stream()
                .map(badge -> badge.getUBCSid().getCSName())
                .distinct()
                .collect(Collectors.toList());

        requestData.put("skills", skillList);
        List<companyDTO> companyDTOList = new ArrayList<>();

        try {
            // 외부 API 호출 및 응답 처리
            List<Map<String, Object>> response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestData)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                    .block();
            System.out.println("Response data: " + response);

            for (Map<String, Object> companyData : response) {
                String comName = (String) companyData.get("company_names");
                Integer comResult = (Integer) companyData.get("result");
                String comSkills = (String) companyData.get("extracted_skills");
                Double comSimilarity = Double.parseDouble(companyData.get("similarity").toString());
                String comPosition = switch (comResult) {
                    case 1 -> "application";
                    case 2 -> "frontend";
                    case 3 -> "data";
                    case 4 -> "backend";
                    case 5 -> "security";
                    default -> "unknown";
                };
                List<Company> companies = companyRepository.findByCOMNameAndCOMPosition(comName, comPosition);

                for (Company company : companies) {
                    if (companyDTOList.size() >= 4) {
                        return companyDTOList;
                    }
                    companyDTO dto = new companyDTO();
                    dto.setId(company.getCOMId());
                    dto.setTitle(company.getCOMName());
                    dto.setCategory(company.getCOMPosition());
                    dto.setName(company.getCOMDescription());
                    double similarityPercentage = Math.round(comSimilarity * 10000) / 100.0;
                    dto.setCongruence(similarityPercentage);
                    dto.setImgUrl(company.getCOMImgUrl());
                    dto.setOpeningUrl(company.getCOMOpeningUrl());
                    companyDTOList.add(dto);
                }
            }
            System.out.println("Matching Company DTOs: " + companyDTOList);

        } catch (WebClientResponseException e) {
            System.err.println("API 호출 오류: " + e.getResponseBodyAsString());
            throw new RuntimeException("외부 API 호출 실패: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("API 호출 오류: " + e.getMessage());
            throw new RuntimeException("외부 API 처리 중 오류 발생", e);
        }
        return companyDTOList;
    }
}
