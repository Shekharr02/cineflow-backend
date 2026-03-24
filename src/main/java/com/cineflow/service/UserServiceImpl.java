package com.cineflow.service;

import com.cineflow.dto.LoginRequest;
import com.cineflow.dto.UserRequest;
import com.cineflow.dto.UserResponse;
import com.cineflow.entity.User;
import com.cineflow.exception.InvalidCredentialsException;
import com.cineflow.exception.UserAlreadyExistsException;
import com.cineflow.exception.UserNotFoundException;
import com.cineflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse register (UserRequest request){
        userRepository.findByEmail(request.getEmail()).ifPresent(u->{
                throw new UserAlreadyExistsException("User already exists");});
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
