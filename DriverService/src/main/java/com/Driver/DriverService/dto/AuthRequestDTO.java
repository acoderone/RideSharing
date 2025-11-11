package com.Driver.DriverService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthRequestDTO {
    private String email;
    private String password;
    private double longitude;
    private double latitude;
}
