package com.example.netflixBackend;

import com.example.netflixBackend.dto.FavoriteResponse;
import com.example.netflixBackend.mapper.FavoriteMapper;
import com.example.netflixBackend.model.Content;
import com.example.netflixBackend.model.Favorite;
import com.example.netflixBackend.model.User;
import com.example.netflixBackend.repository.ContentRepository;
import com.example.netflixBackend.repository.FavoriteRepository;
import com.example.netflixBackend.repository.UserRepository;
import com.example.netflixBackend.service.impl.FavoriteServiceImpl;
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

class FavoriteServiceImplTest {
    @Mock
    private FavoriteRepository favoriteRepository;
    @Mock
    private ContentRepository contentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FavoriteMapper favoriteMapper;
    @InjectMocks
    private FavoriteServiceImpl favoriteService;

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
    void addFavorite_alreadyExists() {
        when(contentRepository.findById(any())).thenReturn(Optional.of(new Content()));
        when(favoriteRepository.existsByUserAndContent(any(), any())).thenReturn(true);
        assertThrows(RuntimeException.class, () -> favoriteService.addFavorite(1L));
    }

    @Test
    void addFavorite_success() {
        when(contentRepository.findById(any())).thenReturn(Optional.of(new Content()));
        when(favoriteRepository.existsByUserAndContent(any(), any())).thenReturn(false);
        when(favoriteRepository.save(any())).thenReturn(new Favorite());
        when(favoriteMapper.toFavoriteResponse(any())).thenReturn(new FavoriteResponse());
        FavoriteResponse response = favoriteService.addFavorite(1L);
        assertNotNull(response);
    }
} 