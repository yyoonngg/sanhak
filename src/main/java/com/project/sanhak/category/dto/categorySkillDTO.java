package com.project.sanhak.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class categorySkillDTO {
    private String category;
    private List<skillDTO> skills;
}
