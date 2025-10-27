package com.rideService.RideService.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class RideRequestEvent {
    private Long userId;
    private double Origin_Latitude;
    private double Origin_Longitude;
    private double Drop_Latitude;
    private double Drop_Longitude;
    private String rideId;
    private String requestedAt;



}
