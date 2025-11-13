package com.userService.UserService.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
