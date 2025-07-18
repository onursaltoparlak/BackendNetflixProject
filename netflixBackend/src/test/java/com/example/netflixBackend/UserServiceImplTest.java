package com.example.netflixBackend;

import com.example.netflixBackend.dto.UserRegisterRequest;
import com.example.netflixBackend.dto.UserResponse;
import com.example.netflixBackend.mapper.UserMapper;
import com.example.netflixBackend.model.Role;
import com.example.netflixBackend.model.User;
import com.example.netflixBackend.repository.UserRepository;
import com.example.netflixBackend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_success() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password");
        User user = User.builder().id(1L).username("testuser").email("test@example.com").password("encoded").role(Role.USER).build();
        when(userMapper.toUser(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toUserResponse(any())).thenReturn(new UserResponse());
        UserResponse response = userService.registerUser(request);
        assertNotNull(response);
        verify(userRepository).save(any());
    }

    @Test
    void registerUser_alreadyExists() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));
        assertThrows(RuntimeException.class, () -> userService.registerUser(request));
    }
} 