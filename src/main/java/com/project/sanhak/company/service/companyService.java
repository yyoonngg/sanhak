package com.project.sanhak.company.service;

import com.project.sanhak.company.repository.companyRepository;
import com.project.sanhak.company.repository.companyReqRepository;
import com.project.sanhak.company.repository.companyReqSkilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class companyService {
    @Autowired
    private companyRepository companyRepository;

    @Autowired
    private companyReqRepository companyReqRepository;

    @Autowired
    private companyReqSkilRepository companyReqSkilRepository;
}
