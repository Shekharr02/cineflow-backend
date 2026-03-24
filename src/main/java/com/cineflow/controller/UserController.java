package com.cineflow.controller;

import com.cineflow.dto.UserRequest;
import com.cineflow.dto.UserResponse;
import com.cineflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest request) {
        return userService.register(request);
    }
}
