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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ride")
public class RideController {
    @Autowired
    private RideRequestService service;
    @PostMapping("/request")
    public ResponseEntity<RideRequestDTO>requestRide(@RequestBody User user){
         return ResponseEntity.ok().body(service.createRideRequest(user));
    }
}
