package com.rideService.RideService.service;

import com.rideService.RideService.event.RideRequestEvent;
import com.rideService.RideService.event.RiderAssignmentEvent;
import com.rideService.RideService.event.RiderLocationEvent;
import com.rideService.RideService.model.Ride;
import com.rideService.RideService.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RideService {
    private static final String TOPIC="Ride-Assignment";

    @Autowired
    private KafkaTemplate<String,RideRequestEvent>kafkaTemplate;
    @Autowired
    private RideRepository rideRepository;


    public void assignRide(RideRequestEvent rideEvent){

      kafkaTemplate.send(TOPIC,rideEvent);
        System.out.println("RideEvent "+rideEvent.getRideId());

    }


}
