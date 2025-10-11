package com.userService.UserService.service;

import com.userService.UserService.dto.UserRequestDTO;
import com.userService.UserService.dto.UserResponseDTO;
import com.userService.UserService.model.User;
import com.userService.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserResponseDTO registerUser(UserRequestDTO userDTO){

        Optional<User> find_User=userRepository.findByEmail(userDTO.getEmail());
        System.out.println(userDTO.getEmail());
        if(find_User.isPresent()){
            throw new RuntimeException("User is already Present");
        }
        User user=new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setNoOfRides(0L);
        userRepository.save(user);
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setNoOfTrips(user.getNoOfRides());
        return userResponseDTO;
    }
}
