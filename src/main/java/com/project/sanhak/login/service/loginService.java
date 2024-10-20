package com.project.sanhak.login.service;

import com.project.sanhak.login.dto.loginDTO;
import com.project.sanhak.login.repository.loginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class loginService {
    @Autowired
    private loginRepository loginRepository;

}