package com.rideService.RideService.listener;

import com.rideService.RideService.event.RideRequestEvent;
import com.rideService.RideService.event.RiderAssignmentEvent;
import com.rideService.RideService.model.Status;
import com.rideService.RideService.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RideRequestListener {
    @Autowired
    private RideService rideService;


    @KafkaListener(
            topics = "rideRequests",
            groupId = "Ride-service-group"
    )
    public void consume(RideRequestEvent event){
         RiderAssignmentEvent riderAssignmentEvent=new RiderAssignmentEvent();
        riderAssignmentEvent.setRideId(event.getRideId());
        riderAssignmentEvent.setRequestedAt(event.getRequestedAt());
        riderAssignmentEvent.setRiderId(null);
        riderAssignmentEvent.setOrigin_Latitude(event.getOrigin_Latitude());
        riderAssignmentEvent.setDrop_Longitude(event.getDrop_Longitude());
        riderAssignmentEvent.setDrop_Latitude(event.getDrop_Latitude());
        riderAssignmentEvent.setStatus(Status.PENDING);
        riderAssignmentEvent.setOrigin_Longitude(event.getOrigin_Longitude());
        riderAssignmentEvent.setUserId(event.getUserId());
       rideService.assignRide(riderAssignmentEvent);
        System.out.println("Received ride request event: " + event.getUserId());

        // You can also call a service here if needed
    }
}
