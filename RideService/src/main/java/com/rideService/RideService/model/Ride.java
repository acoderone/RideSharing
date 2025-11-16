package com.rideService.RideService.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String rideId;

    @Column(nullable = false)
    private Double pickupLongitude;

    @Column(nullable = false)
    private Double pickupLatitude;

    @Column(nullable = false)
    private Double destinationLongitude;

    @Column(nullable = false)
    private Double destinationLatitude;

    @Column(nullable = false)
    private Long userId;

   @Column
    private Long riderId;

    @Column(nullable = false)
    private Double ridePrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;


}
