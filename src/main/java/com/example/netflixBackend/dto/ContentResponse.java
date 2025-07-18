package com.example.netflixBackend.dto;

import com.example.netflixBackend.model.ContentType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContentResponse {
    private Long id;
    private String title;
    private ContentType type;
    private LocalDate releaseDate;
    private String genre;
    private String castList;
    private String director;
    private String description;
    private BigDecimal imdbRating;
} 