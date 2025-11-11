package com.rideService.RideService.event;

import com.rideService.RideService.model.Status;
import lombok.Data;

@Data
public class RiderAssignmentEvent {
    private Long userId;
    private double Origin_Latitude;
    private double Origin_Longitude;
    private double Drop_Latitude;
    private double Drop_Longitude;
    private String rideId;
    private String requestedAt;
    private Long riderId;
}
