package com.rideService.RideService.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String rideId;
    private Double pickupLongitude;
    private Double pickupLatitude;
    private Double destinationLongitude;
    private Double destinationLatitude;
    private Long userId;
    private Long riderId;
    private Double ridePrice;
    @Enumerated(EnumType.STRING)
    private Status status;


}
