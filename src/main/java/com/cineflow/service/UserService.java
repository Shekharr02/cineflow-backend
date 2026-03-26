package com.cineflow.service;

import com.cineflow.dto.LoginRequest;
import com.cineflow.dto.LoginResponse;
import com.cineflow.dto.UserRequest;
import com.cineflow.dto.UserResponse;

public interface UserService {
    UserResponse register (UserRequest request);

    LoginResponse login (LoginRequest request);

    UserResponse getUserByEmail(String email);

}
