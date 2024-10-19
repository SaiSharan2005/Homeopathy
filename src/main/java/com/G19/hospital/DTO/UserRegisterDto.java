package com.G19.hospital.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String[] roles;
}
