package com.rideService.RideService.listener;

import com.rideService.RideService.event.RideRequestEvent;
import com.rideService.RideService.event.RiderAssignmentEvent;
import com.rideService.RideService.model.Ride;
import com.rideService.RideService.model.Status;
import com.rideService.RideService.repository.RideRepository;
import com.rideService.RideService.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RideRequestListener {
    @Autowired
    private RideService rideService;

    @Autowired
    private RideRepository rideRepository;

    @KafkaListener(
            topics = "rideRequests",
            groupId = "Ride-service-group",
            containerFactory = "rideRequestKafkaListenerContainerFactory"
    )
    public void consume(RideRequestEvent event){
        Ride ride=new Ride();
        ride.setRideId(event.getRideId());
        ride.setRidePrice(1000.0);
        ride.setStatus(Status.ACCEPTED);
        ride.setPickupLongitude(event.getOrigin_Longitude());
        ride.setPickupLatitude(event.getOrigin_Latitude());
        ride.setDestinationLatitude(event.getDrop_Latitude());
        ride.setDestinationLongitude(event.getDrop_Longitude());
        ride.setUserId(event.getUserId());
        try{
            rideRepository.save(ride);
        }
        catch(Exception e){
            System.out.println("Not saved"+e);
        }


       rideService.assignRide(event);
        System.out.println("Received ride request event: " + event.getUserId());

        // You can also call a service here if needed
    }
}
