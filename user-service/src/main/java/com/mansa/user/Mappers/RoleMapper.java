package com.mansa.user.Mappers;

import com.mansa.user.Dtos.RoleDto;
import com.mansa.user.Entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);
    List<RoleDto> toDto(List<Role> role);
    List<Role> toEntity(List<RoleDto> roleDto);

}
