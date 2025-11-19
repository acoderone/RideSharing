package com.userService.UserService.controller;

import com.userService.UserService.dto.RideRequestDTO;
import com.userService.UserService.event.RideRequestEvent;
import com.userService.UserService.model.User;
import com.userService.UserService.service.RideRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/ride")
public class RideController {
    @Autowired
    private RideRequestService service;
    @PostMapping("/request")
    public ResponseEntity<RideRequestEvent>requestRide(@RequestHeader("Authorization") String token,
                                                     Principal principal,
                                                     @RequestBody RideRequestDTO requestDTO){
        String email=principal.getName();
         return ResponseEntity.ok().body(service.createRideRequest(email,requestDTO));
    }

    
}
