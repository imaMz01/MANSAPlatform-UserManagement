package com.mansa.user.Mappers;

import com.mansa.user.Dtos.LogDto;
import com.mansa.user.Entities.Log;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface LogMapper {
    LogDto toDto(Log user);
    Log toEntity(LogDto userDto);
    List<LogDto> toDto(List<Log> user);
}
