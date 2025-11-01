package com.Driver.DriverService.service;

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

    /**
     * Adds or updates a driver's current location in Redis GEO store.
     * Redis expects longitude first, then latitude.
     */
    public void addDriverTemplate(String driverId, double longitude, double latitude) {
        stringRedisTemplate.opsForGeo().add(KEY, new Point(longitude, latitude), driverId);
    }

    /**
     * Finds nearby drivers within a 2 km radius of the given coordinates.
     */
    public List<GeoResult<RedisGeoCommands.GeoLocation<String>>> findNearByDrivers(double latitude, double longitude) {
        // ⚠️ FIXED: Redis expects longitude first, not latitude
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

        if (results == null) {
            return List.of(); // return empty list instead of null
        }

        return results.getContent();
    }

    /**
     * Retrieves the last known location of a specific driver.
     */
    public Point getDriverLocation(String driverId) {
        List<Point> points = stringRedisTemplate.opsForGeo().position(KEY, driverId);
        return (points != null && !points.isEmpty()) ? points.get(0) : null;
    }
}
