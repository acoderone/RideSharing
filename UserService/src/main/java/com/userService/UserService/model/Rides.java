package com.userService.UserService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Ride_Table")
public class Rides {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long Id;
    @Column(unique = true,nullable = false)
    private String rideId;
    @Column(nullable = false)
    private String riderId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private double pickupLongitude;
    @Column(nullable = false)
    private double pickupLatitude;
    @Column(nullable = false)
    private double destinationLongitude;
    @Column(nullable = false)
    private double destinationLatitude;
    @Column(nullable = false)
    private LocalDateTime requestedAt;

}
