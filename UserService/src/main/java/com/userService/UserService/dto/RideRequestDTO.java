package com.userService.UserService.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RideRequestDTO {

    private Double origin_latitude;
    private Double origin_longitude;
    private Double destination_latitude;
    private Double destination_longitude;
    private String rideId;
    private String RequestedAt;
}
