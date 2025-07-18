package com.example.netflixBackend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FavoriteResponse {
    private Long id;
    private Long contentId;
    private String contentTitle;
    private LocalDateTime createdAt;
} 