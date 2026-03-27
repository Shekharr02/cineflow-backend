package com.cineflow.service;

import com.cineflow.dto.LoginRequest;
import com.cineflow.dto.LoginResponse;
import com.cineflow.dto.UserRequest;
import com.cineflow.dto.UserResponse;
import com.cineflow.entity.User;
import com.cineflow.enums.Role;
import com.cineflow.exception.InvalidCredentialsException;
import com.cineflow.exception.UserAlreadyExistsException;
import com.cineflow.exception.UserNotFoundException;
import com.cineflow.repository.UserRepository;
import com.cineflow.security.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponse register (UserRequest request){
        userRepository.findByEmail(request.getEmail()).ifPresent(u->{
                throw new UserAlreadyExistsException("User already exists");});
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode( request.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    @Override
    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new InvalidCredentialsException("Invalid password");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new LoginResponse(token, user.getEmail(),user.getRole().name());
    }

    @Override
    public UserResponse getUserByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        return modelMapper.map(user, UserResponse.class);
    }

}
