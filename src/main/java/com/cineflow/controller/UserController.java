package com.cineflow.controller;

import com.cineflow.dto.LoginRequest;
import com.cineflow.dto.LoginResponse;
import com.cineflow.dto.UserRequest;

import com.cineflow.dto.UserResponse;
import com.cineflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public  ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request){
        return ResponseEntity.ok(userService.login(request));
    }

}
