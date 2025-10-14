package com.Driver.DriverService.controller;

import com.Driver.DriverService.dto.AuthRequestDTO;
import com.Driver.DriverService.dto.AuthResponseDTO;
import com.Driver.DriverService.dto.UserRequestDTO;
import com.Driver.DriverService.dto.UserResponseDTO;
import com.Driver.DriverService.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class RiderController {
    @Autowired
    private RiderService riderService;
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO>register(@RequestBody UserRequestDTO user){
        return ResponseEntity.ok(riderService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO>login(@RequestBody AuthRequestDTO user){
        return ResponseEntity.ok(riderService.login(user));
    }
}
