package com.example.netflixBackend;

import com.example.netflixBackend.dto.ReviewRequest;
import com.example.netflixBackend.dto.ReviewResponse;
import com.example.netflixBackend.mapper.ReviewMapper;
import com.example.netflixBackend.model.Content;
import com.example.netflixBackend.model.Review;
import com.example.netflixBackend.model.User;
import com.example.netflixBackend.repository.ContentRepository;
import com.example.netflixBackend.repository.ReviewRepository;
import com.example.netflixBackend.repository.UserRepository;
import com.example.netflixBackend.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ContentRepository contentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReviewMapper reviewMapper;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(new User()));
    }

    @Test
    void createReview_alreadyExists() {
        when(contentRepository.findById(any())).thenReturn(Optional.of(new Content()));
        when(reviewRepository.findByUserAndContent(any(), any())).thenReturn(Optional.of(new Review()));
        ReviewRequest request = new ReviewRequest();
        request.setContentId(1L);
        assertThrows(RuntimeException.class, () -> reviewService.createReview(request));
    }

    @Test
    void createReview_success() {
        when(contentRepository.findById(any())).thenReturn(Optional.of(new Content()));
        when(reviewRepository.findByUserAndContent(any(), any())).thenReturn(Optional.empty());
        when(reviewMapper.toReview(any())).thenReturn(new Review());
        when(reviewRepository.save(any())).thenReturn(new Review());
        when(reviewMapper.toReviewResponse(any())).thenReturn(new ReviewResponse());
        ReviewRequest request = new ReviewRequest();
        request.setContentId(1L);
        ReviewResponse response = reviewService.createReview(request);
        assertNotNull(response);
    }
} 