package com.Driver.DriverService.listener;

import com.Driver.DriverService.event.RiderRequestEvent;
import com.Driver.DriverService.service.RiderLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RiderRequestListener {
    @Autowired
    private RiderLocationService riderLocationService;
    @KafkaListener
            (topics="Ride-Assignment",
                    groupId = "Ride-Service-group")
    public List<GeoResult<RedisGeoCommands.GeoLocation<String>>> consume(RiderRequestEvent event){
        return riderLocationService.findNearByDrivers(event.getOrigin_Longitude(), event.getOrigin_Latitude());
    }
}