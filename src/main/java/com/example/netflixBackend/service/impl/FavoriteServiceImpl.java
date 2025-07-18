package com.example.netflixBackend.service.impl;

import com.example.netflixBackend.dto.FavoriteResponse;
import com.example.netflixBackend.mapper.FavoriteMapper;
import com.example.netflixBackend.model.Content;
import com.example.netflixBackend.model.Favorite;
import com.example.netflixBackend.model.User;
import com.example.netflixBackend.repository.ContentRepository;
import com.example.netflixBackend.repository.FavoriteRepository;
import com.example.netflixBackend.repository.UserRepository;
import com.example.netflixBackend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteMapper favoriteMapper;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }

    @Override
    public FavoriteResponse addFavorite(Long contentId) {
        User user = getCurrentUser();
        Content content = contentRepository.findById(contentId).orElseThrow(() -> new RuntimeException("İçerik bulunamadı"));
        if (favoriteRepository.existsByUserAndContent(user, content)) {
            throw new RuntimeException("Bu içerik zaten favorilerde");
        }
        Favorite favorite = Favorite.builder().user(user).content(content).build();
        return favoriteMapper.toFavoriteResponse(favoriteRepository.save(favorite));
    }

    @Override
    public void removeFavorite(Long contentId) {
        User user = getCurrentUser();
        Content content = contentRepository.findById(contentId).orElseThrow(() -> new RuntimeException("İçerik bulunamadı"));
        favoriteRepository.findAll().stream()
            .filter(fav -> fav.getUser().equals(user) && fav.getContent().equals(content))
            .findFirst()
            .ifPresent(favoriteRepository::delete);
    }

    @Override
    public List<FavoriteResponse> listFavoritesForCurrentUser() {
        User user = getCurrentUser();
        return favoriteRepository.findByUser(user).stream()
                .map(favoriteMapper::toFavoriteResponse)
                .collect(Collectors.toList());
    }
} 