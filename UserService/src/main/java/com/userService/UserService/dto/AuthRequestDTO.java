package com.userService.UserService.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class AuthRequestDTO {
    private String email;
    private String password;
}
