package com.project.sanhak.category.controller;

import com.project.sanhak.category.service.categoryService;
import com.project.sanhak.category.dto.categoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class categoryController {

    @Autowired
    private categoryService categoryService;

    @GetMapping(value = {"/category/{csCate}", "/category"})
    public List<categoryDTO> getCategories(@PathVariable(required = false) Integer csCate) {
        if(csCate == null)
            csCate = 1;

        return categoryService.getSkilNode(csCate);
    }
}
