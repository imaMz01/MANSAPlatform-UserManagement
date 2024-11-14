package com.mansa.authorization.Service;

import com.mansa.authorization.Dto.RoleDto;
import com.mansa.authorization.Dto.UserDto;

import java.util.List;

public interface RoleService {

    RoleDto add(RoleDto roleDto);
    RoleDto getById(String id);
    RoleDto update(RoleDto roleDto);
    List<RoleDto> all();
    String delete(String id);
    RoleDto getByRole(String role);
    UserDto addAuthority(String id, String role);
    UserDto removeAuthority(String id, String role);
}
