package com.example.netflixBackend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewResponse {
    private Long id;
    private Long contentId;
    private String contentTitle;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
} 