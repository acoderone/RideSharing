package com.userService.UserService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;
    private Long noOfRides;
    private double Origin_Latitude;
    private double Origin_Longitude;
    private String phoneNumber;
    private double Drop_Latitude;
    private double Drop_Longitude;
}
