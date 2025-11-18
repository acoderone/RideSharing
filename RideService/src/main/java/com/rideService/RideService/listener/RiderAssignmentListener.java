package com.rideService.RideService.listener;

import com.rideService.RideService.event.RiderAssignmentEvent;
import com.rideService.RideService.event.RiderLocationEvent;
import com.rideService.RideService.model.Ride;
import com.rideService.RideService.repository.RideRepository;
import com.rideService.RideService.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Component
public class RiderAssignmentListener {
    private static final String TOPIC="RIDE";
    @Autowired
    private RideRepository rideRepository;

    @Autowired
    RideService rideService;

@Autowired
    KafkaTemplate<String,RiderAssignmentEvent>kafkaTemplate;
    @KafkaListener(topics = "rider" , groupId = "Ride-service-group", containerFactory = "nearbyDriverKafkaListenerContainerFactory")
public RiderAssignmentEvent consume(RiderLocationEvent event){

    System.out.println("DriverID"+event.getDriverId());
        System.out.println("RideId "+event.getRideId());
    Optional<Ride> ride=rideRepository.findByRideId(event.getRideId());
    if(ride.isEmpty()){
        throw new RuntimeException("Ride not found");
    }
    ride.get().setRiderId(event.getDriverId());
    System.out.println("Ride Id: "+ride.get().getRideId());
    RiderAssignmentEvent riderAssignmentEvent=new RiderAssignmentEvent();
    riderAssignmentEvent.setUserId(ride.get().getUserId());
    riderAssignmentEvent.setRiderId(ride.get().getRiderId());
    riderAssignmentEvent.setOrigin_Longitude(ride.get().getPickupLongitude());
    riderAssignmentEvent.setOrigin_Latitude(ride.get().getPickupLatitude());
    riderAssignmentEvent.setDrop_Latitude(ride.get().getDestinationLatitude());
    riderAssignmentEvent.setDrop_Longitude(ride.get().getDestinationLongitude());
    riderAssignmentEvent.setRequestedAt(LocalDateTime.now());
    riderAssignmentEvent.setRidePrice(ride.get().getRidePrice());
    riderAssignmentEvent.setRideId(ride.get().getRideId());
    kafkaTemplate.send(TOPIC,riderAssignmentEvent);
        rideRepository.save(ride.get());
    System.out.println(riderAssignmentEvent);
    return riderAssignmentEvent;
}
}
