package com.userService.UserService.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UserRequestDTO {
    private String email;
    private String name;
    private Long noOfTrips;
    private String password;
}
