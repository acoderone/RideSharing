package com.Driver.DriverService.event;

import lombok.Data;

@Data
public class RiderLocationEvent {
    private Long driverId;
    private double latitude;
    private double longitude;
}
