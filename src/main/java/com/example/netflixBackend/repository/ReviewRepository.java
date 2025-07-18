package com.example.netflixBackend.repository;

import com.example.netflixBackend.model.Review;
import com.example.netflixBackend.model.User;
import com.example.netflixBackend.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserAndContent(User user, Content content);
    List<Review> findByContent(Content content);
    List<Review> findByUser(User user);
} 