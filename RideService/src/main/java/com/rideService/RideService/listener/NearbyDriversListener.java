package com.rideService.RideService.listener;

import com.rideService.RideService.event.RiderLocationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NearbyDriversListener {
private static String TOPIC="RIDER_LIST";
    @Autowired
    private KafkaTemplate<String,List<RiderLocationEvent>>kafkaTemplate;
    @KafkaListener(topics="Rider_List")
    public void consume(List<RiderLocationEvent>rider_lists){


    }
}
