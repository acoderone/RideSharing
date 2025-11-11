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
            groupId = "Ride-service-group"
    )
    public void consume(RideRequestEvent event){
        Ride ride=new Ride();
        System.out.println("Hii");
        ride.setRideId(event.getRideId());
        System.out.println("Hii");
        ride.setRidePrice(1000.0);
        System.out.println("Hii");
        ride.setStatus(Status.ACCEPTED);
        System.out.println("Hii");
        ride.setPickupLongitude(event.getOrigin_Longitude());
        System.out.println("Hii");
        ride.setPickupLatitude(event.getOrigin_Latitude());
        System.out.println("Hii");

        System.out.println("Hii");
        ride.setDestinationLatitude(event.getDrop_Latitude());
        ride.setUserId(event.getUserId());
        System.out.println("Hii");
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
