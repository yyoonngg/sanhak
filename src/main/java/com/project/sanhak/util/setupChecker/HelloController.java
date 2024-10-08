package com.project.sanhak.util.setupChecker;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class HelloController {
    @GetMapping("/data")
    public String welcome() {
        return "Welcome to Sanhak";
    }
    @GetMapping("/data2")
    public DataResponse getData() {
        DataResponse response = new DataResponse();
        response.setId(1);
        response.setName("Sample Data");
        response.setParent(List.of(2, 3));
        response.setChild(List.of(4, 5));
        response.setPosition(new double[]{1.0, 2.0});

        return response;
    }
}

