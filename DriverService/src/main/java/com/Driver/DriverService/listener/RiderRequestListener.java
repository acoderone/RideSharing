package com.Driver.DriverService.listener;

import com.Driver.DriverService.event.RiderLocationEvent;
import com.Driver.DriverService.event.RiderRequestEvent;
import com.Driver.DriverService.service.RiderLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RiderRequestListener {

    private static final String TOPIC = "rider";

    @Autowired
    private KafkaTemplate<String, RiderLocationEvent> kafkaTemplate;

    @Autowired
    private RiderLocationService riderLocationService;

    @KafkaListener(topics = "Ride-Assignment", groupId = "Ride-service-group")
    public void consume(RiderRequestEvent event) {
        System.out.println("📥 Received ride request: " + event);
        System.out.println(event.getOrigin_Longitude());
        // Find nearest driver
        RiderLocationEvent rider = riderLocationService.findDriver(
                event.getRideId(),
                event.getOrigin_Longitude(),
                event.getOrigin_Latitude()
        );

        System.out.println("🚗 Found driver: " + rider);

        // Publish event to 'rider' topic
        kafkaTemplate.send(TOPIC, rider);

        System.out.println("📤 Sent RiderLocationEvent to topic: " + TOPIC);
    }
}
