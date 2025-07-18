package com.example.netflixBackend.mapper;

import com.example.netflixBackend.dto.FavoriteRequest;
import com.example.netflixBackend.dto.FavoriteResponse;
import com.example.netflixBackend.model.Content;
import com.example.netflixBackend.model.Favorite;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {
    FavoriteMapper INSTANCE = Mappers.getMapper(FavoriteMapper.class);

    @Mapping(target = "content.id", source = "contentId")
    Favorite toFavorite(FavoriteRequest request);

    @Mapping(target = "contentId", source = "content.id")
    @Mapping(target = "contentTitle", source = "content.title")
    FavoriteResponse toFavoriteResponse(Favorite favorite);
} 