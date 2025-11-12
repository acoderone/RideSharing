package com.rideService.RideService.repository;

import com.rideService.RideService.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride,Long> {
Optional<Ride> findByRideId(String rideId);
}
