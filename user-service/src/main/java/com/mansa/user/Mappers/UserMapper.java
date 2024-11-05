package com.mansa.user.Mappers;

import com.mansa.user.Dtos.UserDto;
import com.mansa.user.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    User toEntity(UserDto userDto);
    List<UserDto> toDto(List<User> user);
    List<User> toEntity(List<UserDto> userDto);

}
