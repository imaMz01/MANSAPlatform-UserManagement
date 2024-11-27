package com.mansa.user.Services.UserService;

import com.mansa.user.Dtos.JwtAuthenticationResponse;
import com.mansa.user.Dtos.SignInRequest;
import com.mansa.user.Dtos.SubscriptionDto;
import com.mansa.user.Dtos.UserDto;
import com.mansa.user.Entities.User;
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
    String generateEmailVerificationTokenAndSendEmail(String id);
    String verifyEmail(String token);
    UserDto getCurrentUser();
    UserDto addAuthority(String id, String role);
    UserDto removeAuthority(String id, String role);
    List<SubscriptionDto> userSubscriptions();
    boolean checkEmail(String email);
    User userByEmail(String email);
    String createAccount(String email);
    public UserDto createInviteAccount(UserDto userDto);
}
