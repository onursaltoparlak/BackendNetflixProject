package com.example.netflixBackend.mapper;

import com.example.netflixBackend.dto.ReviewRequest;
import com.example.netflixBackend.dto.ReviewResponse;
import com.example.netflixBackend.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "content.id", source = "contentId")
    Review toReview(ReviewRequest request);

    @Mapping(target = "contentId", source = "content.id")
    @Mapping(target = "contentTitle", source = "content.title")
    ReviewResponse toReviewResponse(Review review);
} 