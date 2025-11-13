package com.userService.UserService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(unique = true,nullable = false)
    private String rideId;
    @Column(nullable = false)
    private Long riderId;
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
