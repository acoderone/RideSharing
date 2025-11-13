package com.userService.UserService.listener;


import com.userService.UserService.event.RiderAssignmentEvent;
import com.userService.UserService.model.Rides;
import com.userService.UserService.repository.RidesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RiderAssignmentListener {

    @Autowired
    private RidesRepository ridesRepository;

    @KafkaListener(topics = "RIDE",groupId = "Ride-service-group",containerFactory = "riderAssignmentKafkaListenerConsumerFactory")
    public void consumer(RiderAssignmentEvent riderAssignmentEvent){
        Rides rides=new Rides();
        rides.setRideId(riderAssignmentEvent.getRideId());
        rides.setRiderId(riderAssignmentEvent.getRiderId());
        rides.setPrice(riderAssignmentEvent.getRidePrice());
        rides.setDestinationLatitude(riderAssignmentEvent.getDrop_Latitude());
        rides.setDestinationLongitude(riderAssignmentEvent.getDrop_Longitude());
        rides.setPickupLatitude(riderAssignmentEvent.getOrigin_Latitude());
        rides.setPickupLongitude(riderAssignmentEvent.getOrigin_Longitude());
        rides.setRequestedAt(riderAssignmentEvent.getRequestedAt());
        ridesRepository.save(rides);

    }
}
