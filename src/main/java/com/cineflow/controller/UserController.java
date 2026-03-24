package com.cineflow.controller;

import com.cineflow.dto.LoginRequest;
import com.cineflow.dto.UserRequest;

import com.cineflow.dto.UserResponse;
import com.cineflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }


}
