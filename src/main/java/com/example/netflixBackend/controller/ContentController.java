package com.example.netflixBackend.controller;

import com.example.netflixBackend.dto.ContentRequest;
import com.example.netflixBackend.dto.ContentResponse;
import com.example.netflixBackend.model.ContentType;
import com.example.netflixBackend.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contents")
public class ContentController {
    @Autowired
    private ContentService contentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ContentResponse> addContent(@RequestBody ContentRequest request) {
        return ResponseEntity.ok(contentService.save(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ContentResponse> updateContent(@PathVariable Long id, @RequestBody ContentRequest request) {
        return ResponseEntity.ok(contentService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ContentResponse>> getAllContents() {
        return ResponseEntity.ok(contentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentResponse> getContentById(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContentResponse>> searchContents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) ContentType type) {
        return ResponseEntity.ok(contentService.searchByFilters(title, genre, type));
    }
} 