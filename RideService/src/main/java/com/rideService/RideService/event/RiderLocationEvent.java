package com.rideService.RideService.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderLocationEvent {
    private String rideId;
    private String driverId;
    private double latitude;
    private double longitude;
}
