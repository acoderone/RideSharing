package com.userService.UserService.event;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RiderAssignmentEvent {
    private Long userId;
    private double Origin_Latitude;
    private double Origin_Longitude;
    private double Drop_Latitude;
    private double Drop_Longitude;
    private String rideId;
    private LocalDateTime requestedAt;
    private String riderId;
    private double ridePrice;
}
