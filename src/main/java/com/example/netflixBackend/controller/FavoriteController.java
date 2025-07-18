package com.example.netflixBackend.controller;

import com.example.netflixBackend.dto.FavoriteRequest;
import com.example.netflixBackend.dto.FavoriteResponse;
import com.example.netflixBackend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<FavoriteResponse> addFavorite(@RequestBody FavoriteRequest request) {
        return ResponseEntity.ok(favoriteService.addFavorite(request.getContentId()));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{contentId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long contentId) {
        favoriteService.removeFavorite(contentId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> listFavorites() {
        return ResponseEntity.ok(favoriteService.listFavoritesForCurrentUser());
    }
} 