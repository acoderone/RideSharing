package com.userService.UserService.service;

import com.userService.UserService.dto.RideRequestDTO;
import com.userService.UserService.event.RideRequestEvent;
import com.userService.UserService.model.User;
import com.userService.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideRequestService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final KafkaTemplate<String,RideRequestEvent>kafkaTemplate;
    private static final String TOPIC="rideRequests";

    @Autowired
    public RideRequestService(KafkaTemplate<String,RideRequestEvent>kafkaTemplate,UserRepository userRepository,JwtService jwtService){
        this.kafkaTemplate=kafkaTemplate;
        this.userRepository=userRepository;
        this.jwtService=jwtService;

    }


    public RideRequestEvent createRideRequest(String email, RideRequestDTO dto){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user==null)
          throw new RuntimeException("User not found");

        // 1. validate
        if (dto.getOrigin_latitude() == 0.0 || dto.getDestination_longitude() == 0.0) {
            throw new IllegalArgumentException("Origin and Destination must be provided");
        }



        // 3. Convert DTO -> Event
        RideRequestEvent event = new RideRequestEvent();
        event.setRideId(String.valueOf(UUID.randomUUID()));
        event.setUserId(user.getId());
        event.setOrigin_Latitude(dto.getOrigin_latitude());
        event.setOrigin_Longitude(dto.getOrigin_longitude());
        event.setDrop_Latitude(dto.getDestination_latitude());
        event.setDrop_Longitude(dto.getDestination_longitude());
        event.setRequestedAt(String.valueOf(LocalDate.now()));

        // 4. Publish
        kafkaTemplate.send(TOPIC, event);

        return event;
    }
}
