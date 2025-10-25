package com.userService.UserService.service;

import com.userService.UserService.dto.RideRequestDTO;
import com.userService.UserService.event.RideRequestEvent;
import com.userService.UserService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RideRequestService {
    private final KafkaTemplate<String,RideRequestEvent>kafkaTemplate;
    private static final String TOPIC="rideRequests";

    @Autowired
    public RideRequestService(KafkaTemplate<String,RideRequestEvent>kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }


    public RideRequestDTO createRideRequest(User user){
        // 1. validate
        if (user.getOrigin_Latitude() == 0.0 || user.getDrop_Latitude() == 0.0) {
            throw new IllegalArgumentException("Origin and Destination must be provided");
        }

        // 2. Build DTO (returned to user/controller)
        RideRequestDTO dto = new RideRequestDTO();
        dto.setRideId(UUID.randomUUID().toString());
        dto.setUserID(user.getId());
        dto.setOrigin_latitude(user.getOrigin_Latitude());
        dto.setOrigin_longitude(user.getOrigin_Longitude());
        dto.setDestination_latitude(user.getDrop_Latitude());
        dto.setDestination_longitude(user.getDrop_Longitude());
        dto.setRequestedAt(Instant.now().toString());

        // 3. Convert DTO -> Event
        RideRequestEvent event = new RideRequestEvent();
        event.setRideId(dto.getRideId());
        event.setUserId(dto.getUserID());
        event.setOrigin_Latitude(dto.getOrigin_latitude());
        event.setOrigin_Longitude(dto.getOrigin_longitude());
        event.setDrop_Latitude(dto.getDestination_latitude());
        event.setDrop_Longitude(dto.getDestination_longitude());
        event.setRequestedAt(dto.getRequestedAt());

        // 4. Publish
        kafkaTemplate.send(TOPIC, event);

        return dto;
    }
}
