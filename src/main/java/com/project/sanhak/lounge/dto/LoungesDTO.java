package com.project.sanhak.lounge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO representing lounge information")
public class LoungesDTO {

    @Schema(description = "The unique ID of the lounge")
    private int id;
    @Schema(description = "Number of likes the lounge has received")
    private int likes;
    @Schema(description = "Number of views the lounge has received")
    private int viewCount;
    @Schema(description = "Number of badges associated with the lounge")
    private int badgeCount;
    @Schema(description = "Number of roadmaps associated with the lounge")
    private int roadmapCount;
    @Schema(description = "Number of cards associated with the lounge")
    private int cardCount;
    @Schema(description = "The name of the lounge")
    private String name;
    @Schema(description = "Position or role associated with the lounge")
    private String position;
    @Schema(description = "URL of the lounge's image")
    private String imageURL;
}
