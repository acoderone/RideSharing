package com.userService.UserService.controller;

import com.userService.UserService.dto.UserRequestDTO;
import com.userService.UserService.dto.UserResponseDTO;
import com.userService.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    private ResponseEntity<UserResponseDTO>register(@RequestBody UserRequestDTO userDto){
        return ResponseEntity.ok(userService.registerUser(userDto));
    }
}
