package com.Driver.DriverService.service;

import com.Driver.DriverService.event.RiderLocationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiderLocationService {
    public static final String KEY="drivers";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void addDriverTemplate(String driverId, double latitude,double longitude){
        stringRedisTemplate.opsForGeo().add(KEY,new Point(latitude,longitude),driverId);

    }

    public List<GeoResult<RedisGeoCommands.GeoLocation<String>>> findNearByDrivers(double latitude, double longitude, double radiusKm){
        Circle area=new Circle(new Point(latitude,longitude),new Distance(radiusKm, Metrics.KILOMETERS));
        GeoResults<RedisGeoCommands.GeoLocation<String>>results=stringRedisTemplate.opsForGeo().radius(KEY,area,RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().sortAscending());
        assert results != null;
        return results.getContent();

    }

    public Point getDriverLocation(String driverId){
        List<Point>Points=stringRedisTemplate.opsForGeo().position(KEY,driverId);
        return (Points!=null && !Points.isEmpty())?Points.get(0):null;
    }


}
