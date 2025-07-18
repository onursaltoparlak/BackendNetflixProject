package com.example.netflixBackend.repository;

import com.example.netflixBackend.model.Favorite;
import com.example.netflixBackend.model.User;
import com.example.netflixBackend.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserAndContent(User user, Content content);
    List<Favorite> findByUser(User user);
} 