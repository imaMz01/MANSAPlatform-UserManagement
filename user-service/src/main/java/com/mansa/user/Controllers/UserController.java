package com.mansa.user.Controllers;

import com.mansa.user.Annotations.RequestLogger;
import com.mansa.user.Dtos.JwtAuthenticationResponse;
import com.mansa.user.Dtos.SignInRequest;
import com.mansa.user.Dtos.SubscriptionDto;
import com.mansa.user.Dtos.UserDto;
import com.mansa.user.Services.UserService.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    @PostMapping("/users/register")
    @RequestLogger(action = "register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto){
        ResponseEntity<UserDto> response= new ResponseEntity<>(userService.add(userDto), HttpStatus.CREATED);
        if(response.getStatusCode() == HttpStatus.CREATED){
            userService.generateEmailVerificationTokenAndSendEmail(response.getBody().getId());
        }
        return response;
    }

    @RequestLogger(action = "all users")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDto>> all(){
        return new ResponseEntity<>(userService.all(),HttpStatus.OK);
    }

    @RequestLogger(action = "user by id")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE) or hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE)")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> userById(@PathVariable String id){
        return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
    }

    @RequestLogger(action = "update user")
    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.ADMIN_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE)")
    @PutMapping("/users/me")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.update(userDto),HttpStatus.OK);
    }

    @RequestLogger(action = "change user status")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PutMapping("/admin/users/{id}/status")
    public ResponseEntity<UserDto> changeStatus(@PathVariable String id){
        return new ResponseEntity<>(userService.changeStatus(id),HttpStatus.OK);
    }

    @RequestLogger(action = "login")
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SignInRequest signInRequest) throws Exception {
        return new ResponseEntity<>(userService.signIn(signInRequest),HttpStatus.OK);
    }

    @RequestLogger(action = "logout")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("hii this is logout ");
        userService.logout(request, response);
        return new ResponseEntity<>("Logout successfully",HttpStatus.OK);

    }

    @RequestLogger(action = "generate Email Validation Token")
    @GetMapping("/generateEmailValidationToken/{id}")
    public ResponseEntity<String> generateToken(@PathVariable String id){
        return new ResponseEntity<>(userService.generateEmailVerificationTokenAndSendEmail(id),HttpStatus.OK);
    }



    @GetMapping("/users/verify/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token){
        return new ResponseEntity<>(userService.verifyEmail(token),HttpStatus.OK);
    }

    @RequestLogger(action = "current user")
    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.ADMIN_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE)")
    @GetMapping("/users/me")
    public ResponseEntity<UserDto> getCurrentUser(){
        return new ResponseEntity<>(userService.getCurrentUser(),HttpStatus.OK);
    }

    @RequestLogger(action = "add authority")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PutMapping("/admin/addAuthority/{id}/{role}")
    public ResponseEntity<UserDto> addAuthority(@PathVariable String id, @PathVariable String role){
        return new ResponseEntity<>(userService.addAuthority(id,role),HttpStatus.OK);
    }

    @RequestLogger(action = "remove authority")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PutMapping("/admin/removeAuthority/{id}/{role}")
    public ResponseEntity<UserDto> removeAuthority(@PathVariable String id, @PathVariable String role){
        return new ResponseEntity<>(userService.removeAuthority(id,role),HttpStatus.OK);
    }

    @RequestLogger(action = "user subscriptions")
    @PreAuthorize("hasRole(@Statics.SUBSCRIBER_ROLE)")
    @GetMapping("/userSubscriptions")
    public ResponseEntity<List<SubscriptionDto>> userSubscriptions(){
        return new ResponseEntity<>(userService.userSubscriptions(),HttpStatus.OK);
    }

    @RequestLogger(action = "get users by company")
    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE)")
    @GetMapping("/usersCompany/{name}")
    public ResponseEntity<List<UserDto>> usersCompany(@PathVariable String name){
        return new ResponseEntity<>(userService.usersByCompany(name),HttpStatus.OK);
    }
}
