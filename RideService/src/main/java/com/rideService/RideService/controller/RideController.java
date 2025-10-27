package com.rideService.RideService.controller;

import com.rideService.RideService.event.RideRequestEvent;
import com.rideService.RideService.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ride")
public class RideController {
    @Autowired
    private RideService rideService;
    @GetMapping("/send")
    public ResponseEntity<String>sendRideRequest(){
        return ResponseEntity.ok("Hii");
    }
}
