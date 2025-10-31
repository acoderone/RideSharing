package com.Driver.DriverService.controller;

import com.Driver.DriverService.event.RiderLocationEvent;
import com.Driver.DriverService.service.RiderLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/rider")
public class RiderLocationController {
    @Autowired
    private RiderLocationService riderLocationService;
    @PostMapping("/trackLocation")
    public ResponseEntity<String>addLocation(Principal principal,@RequestBody RiderLocationEvent event){
       if(principal!=null){
           riderLocationService.addDriverTemplate(event.getDriverId(), event.getLatitude() ,event.getLongitude());

       }
       else{
           ResponseEntity.status(403).body("User not logged In");
       }
        return ResponseEntity.ok("Rider got added");
    }
}
