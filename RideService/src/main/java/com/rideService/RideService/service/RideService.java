package com.rideService.RideService.service;

import com.rideService.RideService.event.RiderAssignmentEvent;
import com.rideService.RideService.model.Ride;
import com.rideService.RideService.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RideService {
    private static final String TOPIC="Ride-Assignment";
    @Autowired
    private KafkaTemplate<String,RiderAssignmentEvent>kafkaTemplate;
    @Autowired
    private RideRepository rideRepository;
    public void assignRide(RiderAssignmentEvent rideEvent){
        Ride ride=new Ride();
        ride.setRideId(rideEvent.getRideId());
        ride.setRidePrice(0.0);
        ride.setDestinationLatitude(rideEvent.getDrop_Latitude());
        ride.setStatus(rideEvent.getStatus());
        ride.setUserId(rideEvent.getUserId());
        ride.setDestinationLongitude(rideEvent.getDrop_Longitude());
        ride.setPickupLatitude(rideEvent.getOrigin_Latitude());
        ride.setPickupLongitude(rideEvent.getOrigin_Longitude());
        ride.setRiderId(rideEvent.getRiderId());
        rideRepository.save(ride);
      kafkaTemplate.send(TOPIC,rideEvent);
        System.out.println("RideEvent "+rideEvent.getRideId());

    }
}
