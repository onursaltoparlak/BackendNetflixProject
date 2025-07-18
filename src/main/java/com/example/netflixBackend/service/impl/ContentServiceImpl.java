package com.example.netflixBackend.service.impl;

import com.example.netflixBackend.dto.ContentRequest;
import com.example.netflixBackend.dto.ContentResponse;
import com.example.netflixBackend.mapper.ContentMapper;
import com.example.netflixBackend.model.Content;
import com.example.netflixBackend.model.ContentType;
import com.example.netflixBackend.repository.ContentRepository;
import com.example.netflixBackend.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContentMapper contentMapper;

    @Override
    public ContentResponse save(ContentRequest request) {
        Content content = contentMapper.toContent(request);
        return contentMapper.toContentResponse(contentRepository.save(content));
    }

    @Override
    public ContentResponse update(Long id, ContentRequest request) {
        Optional<Content> optionalContent = contentRepository.findById(id);
        if (optionalContent.isPresent()) {
            Content content = optionalContent.get();
            Content updated = contentMapper.toContent(request);
            updated.setId(id);
            return contentMapper.toContentResponse(contentRepository.save(updated));
        }
        throw new RuntimeException("Content not found");
    }

    @Override
    public void delete(Long id) {
        contentRepository.deleteById(id);
    }

    @Override
    public ContentResponse getById(Long id) {
        return contentRepository.findById(id)
                .map(contentMapper::toContentResponse)
                .orElseThrow(() -> new RuntimeException("Content not found"));
    }

    @Override
    public List<ContentResponse> getAll() {
        return contentRepository.findAll().stream()
                .map(contentMapper::toContentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContentResponse> searchByFilters(String title, String genre, ContentType type) {
        List<Content> filtered = contentRepository.findAll();
        if (title != null && !title.isEmpty()) {
            filtered = filtered.stream().filter(c -> c.getTitle() != null && c.getTitle().toLowerCase().contains(title.toLowerCase())).collect(Collectors.toList());
        }
        if (genre != null && !genre.isEmpty()) {
            filtered = filtered.stream().filter(c -> c.getGenre() != null && c.getGenre().toLowerCase().contains(genre.toLowerCase())).collect(Collectors.toList());
        }
        if (type != null) {
            filtered = filtered.stream().filter(c -> c.getType() == type).collect(Collectors.toList());
        }
        return filtered.stream().map(contentMapper::toContentResponse).collect(Collectors.toList());
    }
} 