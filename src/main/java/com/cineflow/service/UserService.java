package com.cineflow.service;

import com.cineflow.dto.UserRequest;
import com.cineflow.dto.UserResponse;
import com.cineflow.entity.User;
import com.cineflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse register (UserRequest request){
        userRepository.findByEmail(request.getEmail()).ifPresent(u->{
                throw new RuntimeException("User already exists");});
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser = userRepository.save(user);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }
}
