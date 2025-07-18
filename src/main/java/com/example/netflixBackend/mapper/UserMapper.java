package com.example.netflixBackend.mapper;

import com.example.netflixBackend.model.User;
import com.example.netflixBackend.dto.UserResponse;
import com.example.netflixBackend.dto.UserRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toUserResponse(User user);

    User toUser(UserRegisterRequest userRegisterRequest);
} 