package com.example.netflixBackend.service;

import com.example.netflixBackend.dto.FavoriteResponse;
import java.util.List;

public interface FavoriteService {
    FavoriteResponse addFavorite(Long contentId);
    void removeFavorite(Long contentId);
    List<FavoriteResponse> listFavoritesForCurrentUser();
} 