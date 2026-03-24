package com.cineflow.service;

import com.cineflow.dto.UserRequest;
import com.cineflow.dto.UserResponse;

public interface UserService {
    UserResponse register (UserRequest request);


}
