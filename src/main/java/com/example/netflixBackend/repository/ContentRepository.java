package com.example.netflixBackend.repository;

import com.example.netflixBackend.model.Content;
import com.example.netflixBackend.model.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByTitleContainingIgnoreCase(String title);
    List<Content> findByGenreContainingIgnoreCase(String genre);
    List<Content> findByType(ContentType type);
} 