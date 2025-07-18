package com.example.netflixBackend.service;

import com.example.netflixBackend.dto.ContentRequest;
import com.example.netflixBackend.dto.ContentResponse;
import com.example.netflixBackend.model.ContentType;
import java.util.List;

public interface ContentService {
    ContentResponse save(ContentRequest request);
    ContentResponse update(Long id, ContentRequest request);
    void delete(Long id);
    ContentResponse getById(Long id);
    List<ContentResponse> getAll();
    List<ContentResponse> searchByFilters(String title, String genre, ContentType type);
} 