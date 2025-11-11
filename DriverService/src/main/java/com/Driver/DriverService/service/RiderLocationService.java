package com.Driver.DriverService.service;

import com.Driver.DriverService.event.RiderLocationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiderLocationService {

    private static final String KEY = "drivers";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void addDriverTemplate(String driverId, double longitude, double latitude,String status) {
        stringRedisTemplate.opsForGeo().add(KEY, new Point(longitude, latitude), driverId);
        stringRedisTemplate.opsForHash().put("driver"+driverId+":META","status",status);
    }


    public void updateDriverStatus(double driverId,String status){
      stringRedisTemplate.opsForHash().put("driver"+driverId+"META","status",status);
      if("offline".equalsIgnoreCase(status)){
          stringRedisTemplate.opsForZSet().remove(KEY,driverId);
      }
    }


    public RiderLocationEvent findDriver(String rideId,double longitude, double latitude) {

        Point point = new Point(longitude, latitude);
        Circle area = new Circle(point, new Distance(2, Metrics.KILOMETERS));

        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                stringRedisTemplate.opsForGeo().radius(
                        KEY,
                        area,
                        RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                                .includeDistance()
                                .includeCoordinates()
                                .sortAscending()
                );


        assert results != null;
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>>riders= results.getContent().stream().filter(e->{
            String driverId=e.getContent().getName();
            Object status=stringRedisTemplate.opsForHash().get("driver"+driverId+":META","status");
            return "available".equalsIgnoreCase(String.valueOf(status));
        }).toList();
        GeoResult<RedisGeoCommands.GeoLocation<String>>assignedDriver=riders.get(0);
        String driverId=assignedDriver.getContent().getName();
        stringRedisTemplate.opsForHash().put("driver"+driverId+":META","status","busy");
        stringRedisTemplate.opsForZSet().remove(KEY,driverId);
        return new RiderLocationEvent(rideId,driverId,assignedDriver.getContent().getPoint().getX(),assignedDriver.getContent().getPoint().getY());

    }


    public Point getDriverLocation(String driverId) {
        List<Point> points = stringRedisTemplate.opsForGeo().position(KEY, driverId);
        return (points != null && !points.isEmpty()) ? points.get(0) : null;
    }
}
