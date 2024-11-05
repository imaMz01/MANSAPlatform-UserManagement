package com.mansa.user.Controllers;

import com.mansa.user.Dtos.UserDto;
import com.mansa.user.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.add(userDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> all(){
        return new ResponseEntity<>(userService.all(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> userById(@PathVariable String id){
        return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
    }

    @PutMapping("/me")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.update(userDto),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> changeStatus(@PathVariable String id){
        return new ResponseEntity<>(userService.changeStatus(id),HttpStatus.OK);
    }
}
