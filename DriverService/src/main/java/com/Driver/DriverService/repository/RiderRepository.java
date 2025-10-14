package com.Driver.DriverService.repository;

import com.Driver.DriverService.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RiderRepository extends JpaRepository<Rider,Long> {
    Optional<Rider> findByEmail(String email);
}
