package com.project.sanhak.util.setupChecker;

import lombok.Data;

import java.util.List;

@Data
public class DataResponse {
    private int id;
    private String name;
    private List<Integer> parent;
    private List<Integer> child;
    private double[] position;}
