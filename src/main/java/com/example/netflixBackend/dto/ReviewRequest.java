package com.example.netflixBackend.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long contentId;
    private Integer rating;
    private String comment;
} 