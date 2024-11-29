package com.project.sanhak.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class roadmapUpdateRequest {
    private String name;
    private List<changeRoadmapDTO> updates;
}
