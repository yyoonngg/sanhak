package com.project.sanhak.card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class aiCardDTO {
    private String fromDate;
    private String toDate;
    private String title;
    private List<String> category;
    private List<String> tools;
    private String reflection;
    private String imageUrl;
    private String pdfFile;
    private String sourceUrl;
}
