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
    private int view_cnt;
    @Schema(description = "Number of badges associated with the lounge")
    private int badge_cnt;
    @Schema(description = "Number of roadmaps associated with the lounge")
    private int roadmap_cnt;
    @Schema(description = "Number of cards associated with the lounge")
    private int card_cnt;
    @Schema(description = "The name of the lounge")
    private String name;
    @Schema(description = "Position or role associated with the lounge")
    private String category;
    @Schema(description = "URL of the lounge's image")
    private String imageURL;
    private int user_id;
}
