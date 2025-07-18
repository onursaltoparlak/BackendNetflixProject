package com.example.netflixBackend;

import com.example.netflixBackend.dto.ContentRequest;
import com.example.netflixBackend.dto.ContentResponse;
import com.example.netflixBackend.mapper.ContentMapper;
import com.example.netflixBackend.model.Content;
import com.example.netflixBackend.model.ContentType;
import com.example.netflixBackend.repository.ContentRepository;
import com.example.netflixBackend.service.impl.ContentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContentServiceImplTest {
    @Mock
    private ContentRepository contentRepository;
    @Mock
    private ContentMapper contentMapper;
    @InjectMocks
    private ContentServiceImpl contentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveContent_success() {
        ContentRequest request = new ContentRequest();
        Content content = new Content();
        ContentResponse response = new ContentResponse();
        when(contentMapper.toContent(any())).thenReturn(content);
        when(contentRepository.save(any())).thenReturn(content);
        when(contentMapper.toContentResponse(any())).thenReturn(response);
        ContentResponse result = contentService.save(request);
        assertNotNull(result);
        verify(contentRepository).save(any());
    }

    @Test
    void updateContent_notFound() {
        when(contentRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> contentService.update(1L, new ContentRequest()));
    }
} 