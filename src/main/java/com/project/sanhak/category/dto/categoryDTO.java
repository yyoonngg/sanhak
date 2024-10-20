package com.project.sanhak.category.dto;

import lombok.Data;

import java.util.List;

@Data
public class categoryDTO {
    private int id;
    private String name;
    private List<Integer> parent;
    private List<Integer> child;
    private int[] position;
    private String tag;
}
