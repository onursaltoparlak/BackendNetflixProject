package com.example.netflixBackend.service.impl;

import com.example.netflixBackend.dto.ReviewRequest;
import com.example.netflixBackend.dto.ReviewResponse;
import com.example.netflixBackend.mapper.ReviewMapper;
import com.example.netflixBackend.model.Content;
import com.example.netflixBackend.model.Review;
import com.example.netflixBackend.model.User;
import com.example.netflixBackend.repository.ContentRepository;
import com.example.netflixBackend.repository.ReviewRepository;
import com.example.netflixBackend.repository.UserRepository;
import com.example.netflixBackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewMapper reviewMapper;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }

    @Override
    public ReviewResponse createReview(ReviewRequest request) {
        User user = getCurrentUser();
        Content content = contentRepository.findById(request.getContentId()).orElseThrow(() -> new RuntimeException("İçerik bulunamadı"));
        if (reviewRepository.findByUserAndContent(user, content).isPresent()) {
            throw new RuntimeException("Bu içeriğe zaten yorum yaptınız");
        }
        Review review = reviewMapper.toReview(request);
        review.setUser(user);
        review.setContent(content);
        return reviewMapper.toReviewResponse(reviewRepository.save(review));
    }

    @Override
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request) {
        User user = getCurrentUser();
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Yorum bulunamadı"));
        if (!review.getUser().equals(user)) {
            throw new RuntimeException("Sadece kendi yorumunuzu güncelleyebilirsiniz");
        }
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return reviewMapper.toReviewResponse(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Long reviewId) {
        User user = getCurrentUser();
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Yorum bulunamadı"));
        if (!review.getUser().equals(user)) {
            throw new RuntimeException("Sadece kendi yorumunuzu silebilirsiniz");
        }
        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewResponse> getReviewsByContent(Long contentId) {
        Content content = contentRepository.findById(contentId).orElseThrow(() -> new RuntimeException("İçerik bulunamadı"));
        return reviewRepository.findByContent(content).stream()
                .map(reviewMapper::toReviewResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> getMyReviews() {
        User user = getCurrentUser();
        return reviewRepository.findByUser(user).stream()
                .map(reviewMapper::toReviewResponse)
                .collect(Collectors.toList());
    }
} 