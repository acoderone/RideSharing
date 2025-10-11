package com.userService.UserService.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class UserResponseDTO {
    private String email;
    private String name;
    private Long noOfTrips;
}
