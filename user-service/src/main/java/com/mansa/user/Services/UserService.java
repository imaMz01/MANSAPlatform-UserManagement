package com.mansa.user.Services;

import com.mansa.user.Dtos.JwtAuthenticationResponse;
import com.mansa.user.Dtos.SignInRequest;
import com.mansa.user.Dtos.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {

    UserDto add(UserDto userDto);
    UserDto update(UserDto userDto);
    List<UserDto> all();
    UserDto changeStatus(String id);
    UserDto getById(String id);
    JwtAuthenticationResponse signIn(SignInRequest request);
    void logout(HttpServletRequest request, HttpServletResponse response);
    String generateEmailVerificationToken(String id);
    String verifyEmail(String token);

}
