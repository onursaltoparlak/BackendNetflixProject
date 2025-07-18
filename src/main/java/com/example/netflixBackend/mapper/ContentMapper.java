package com.example.netflixBackend.mapper;

import com.example.netflixBackend.model.Content;
import com.example.netflixBackend.dto.ContentRequest;
import com.example.netflixBackend.dto.ContentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContentMapper {
    ContentMapper INSTANCE = Mappers.getMapper(ContentMapper.class);

    Content toContent(ContentRequest contentRequest);
    ContentResponse toContentResponse(Content content);
} 