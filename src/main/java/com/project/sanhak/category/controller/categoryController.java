package com.project.sanhak.category.controller;

import com.project.sanhak.category.dto.categoryDTO;
import com.project.sanhak.category.service.categoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/category")
public class categoryController {

    @Autowired
    private categoryService categoryService;

    @Operation(summary = "카테고리별 로드맵 호출. 기본은 백엔드.")
    @GetMapping(value = {"/{csCate}", "/"})
    public List<categoryDTO> getCategories(@PathVariable(required = false) String csCate) {
        if (csCate == null)
            csCate = "backend";
        return categoryService.getSkilNode(csCate);
    }


}
