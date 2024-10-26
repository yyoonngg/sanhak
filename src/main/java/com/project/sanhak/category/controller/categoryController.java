package com.project.sanhak.category.controller;

import com.project.sanhak.category.dto.categoryDTO;
import com.project.sanhak.category.service.categoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/category")
public class categoryController {

    @Autowired
    private categoryService categoryService;

    @GetMapping(value = {"/{csCate}", "/"})
    public List<categoryDTO> getCategories(@PathVariable(required = false) String csCate) {
        if (csCate == null)
            csCate = "backend";
        return categoryService.getSkilNode(csCate);
    }


}
