package com.project.sanhak.company.controller;

import com.project.sanhak.company.service.companyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
public class companyController {
    @Autowired
    private companyService companyService;
}