package com.mansa.user.Controllers;

import com.mansa.user.Dtos.JwtAuthenticationResponse;
import com.mansa.user.Dtos.SignInRequest;
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
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto){
        ResponseEntity<UserDto> response= new ResponseEntity<>(userService.add(userDto), HttpStatus.CREATED);
        if(response.getStatusCode() == HttpStatus.CREATED){
            userService.generateEmailVerificationToken(response.getBody().getId());
        }
        return response;
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDto>> all(){
        return new ResponseEntity<>(userService.all(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> userById(@PathVariable String id){
        return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.ADMIN_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE)")
    @PutMapping("/users/me")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.update(userDto),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PutMapping("/admin/users/{id}/status")
    public ResponseEntity<UserDto> changeStatus(@PathVariable String id){
        return new ResponseEntity<>(userService.changeStatus(id),HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SignInRequest signInRequest){
        return new ResponseEntity<>(userService.signIn(signInRequest),HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("hii this is logout ");
        userService.logout(request, response);
        return new ResponseEntity<>("Logout successfully",HttpStatus.OK);

    }


    @GetMapping("/generateEmailValidationToken/{id}")
    public ResponseEntity<String> generateToken(@PathVariable String id){
        return new ResponseEntity<>(userService.generateEmailVerificationToken(id),HttpStatus.OK);
    }


    @GetMapping("/users/verify/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token){
        return new ResponseEntity<>(userService.verifyEmail(token),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.ADMIN_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE)")
    @GetMapping("/users/me")
    public ResponseEntity<UserDto> getCurrentUser(){
        return new ResponseEntity<>(userService.getCurrentUser(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PutMapping("/admin/addAuthority/{id}/{role}")
    public ResponseEntity<UserDto> addAuthority(@PathVariable String id, @PathVariable String role){
        return new ResponseEntity<>(userService.addAuthority(id,role),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PutMapping("/admin/removeAuthority/{id}/{role}")
    public ResponseEntity<UserDto> removeAuthority(@PathVariable String id, @PathVariable String role){
        return new ResponseEntity<>(userService.removeAuthority(id,role),HttpStatus.OK);
    }
}
