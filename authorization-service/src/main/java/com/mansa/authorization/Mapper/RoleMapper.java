package com.mansa.authorization.Mapper;

import com.mansa.authorization.Dto.RoleDto;
import com.mansa.authorization.Entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleMapper {

    RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);
    List<RoleDto> toDto(List<Role> role);
    List<Role> toEntity(List<RoleDto> roleDto);

}
