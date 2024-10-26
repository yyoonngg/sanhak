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
    private List<String> skills;
    private List<String> tools;
    private String reflection;
    private String imageUrl;
    private String pdfName;
    private String pdfUrl;
    private List<String> sourceUrl;
    private String summary;
}
