package com.userService.UserService.repository;

import com.userService.UserService.model.Rides;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RidesRepository extends JpaRepository<Rides, Long> {
}
