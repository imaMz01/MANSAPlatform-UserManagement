package com.mansa.user.Services;

import com.mansa.user.Dtos.UserDto;

import java.util.List;

public interface UserService {

    UserDto add(UserDto userDto);
    UserDto update(UserDto userDto);
    List<UserDto> all();
    UserDto changeStatus(String id);
    UserDto getById(String id);
}
