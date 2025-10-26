package com.userService.UserService.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestEvent {
    private Long userId;
    private double Origin_Latitude;
    private double Origin_Longitude;
    private double Drop_Latitude;
    private double Drop_Longitude;
    private String rideId;
    private String requestedAt;

}
