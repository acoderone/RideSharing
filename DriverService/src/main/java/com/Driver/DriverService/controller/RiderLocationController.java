package com.Driver.DriverService.controller;

import com.Driver.DriverService.event.RiderLocationEvent;
import com.Driver.DriverService.service.RiderLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/rider")
public class RiderLocationController {

    @Autowired
    private RiderLocationService riderLocationService;

    /**
     * Adds or updates a driver's current location in Redis.
     */
    @PostMapping("/trackLocation")
    public ResponseEntity<String> addLocation(Principal principal, @RequestBody RiderLocationEvent event) {
        try {
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("User not logged in");
            }

            if (event == null || event.getDriverId() == null) {
                return ResponseEntity.badRequest()
                        .body("Invalid event payload");
            }

            // Redis expects longitude first, then latitude
            riderLocationService.addDriverTemplate(event.getDriverId(),
                    event.getLongitude(), event.getLatitude());

            return ResponseEntity.ok("Driver location added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while adding driver location: " + e.getMessage());
        }
    }

    /**
     * Fetches nearby drivers within a certain radius.
     */
    @PostMapping("/getDrivers")
    public ResponseEntity<?> getNearbyDrivers(@RequestParam double latitude,
                                              @RequestParam double longitude) {
        try {
            List<Map<String, Object>> driversList = new ArrayList<>();

            List<GeoResult<RedisGeoCommands.GeoLocation<String>>> results =
                    riderLocationService.findNearByDrivers(latitude, longitude);

            if (results == null || results.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No nearby drivers found");
            }

            for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
                Map<String, Object> map = new HashMap<>();
                map.put("driverId", result.getContent().getName());

                Point point = result.getContent().getPoint();
                map.put("longitude", point.getX());
                map.put("latitude", point.getY());

                map.put("distance", result.getDistance().getValue());

                driversList.add(map);
            }

            return ResponseEntity.ok(driversList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while fetching nearby drivers: " + e.getMessage());
        }
    }
}
