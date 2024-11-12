package com.project.sanhak.lounge.controller;

import com.project.sanhak.lounge.dto.LoungesDTO;
import com.project.sanhak.lounge.service.LoungeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lounge")
public class LoungeController {
    @Autowired
    private LoungeService loungeService;
    @Operation(summary = "Retrieve lounges with pagination and sorting options",
            description = "Fetches lounges based on the specified sort option and page number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved lounges",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoungesDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid sort or page option"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all/{sortOption}/{page}")
    public ResponseEntity<Page<LoungesDTO>> getAllLounges(
            @PathVariable int sortOption,
            @PathVariable int page) {
        Page<LoungesDTO> lounges = loungeService.getLounges(sortOption, page);
        return ResponseEntity.ok(lounges);
    }
}