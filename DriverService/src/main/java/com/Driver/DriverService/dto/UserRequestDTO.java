package com.Driver.DriverService.dto;

import com.Driver.DriverService.model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestDTO {

    private String email;
    private String password;
    private String name;
    private String license;

}
