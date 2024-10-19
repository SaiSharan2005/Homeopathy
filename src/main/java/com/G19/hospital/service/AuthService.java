package com.G19.hospital.service;

import com.G19.hospital.model.User;
import com.G19.hospital.DTO.UserLoginDto;
import com.G19.hospital.DTO.UserRegisterDto;
import com.G19.hospital.util.Security.AccessToken;

import java.util.List;

public interface AuthService {
    AccessToken register(UserRegisterDto userRegisterDto);

    AccessToken login(UserLoginDto userLoginDto);


}
