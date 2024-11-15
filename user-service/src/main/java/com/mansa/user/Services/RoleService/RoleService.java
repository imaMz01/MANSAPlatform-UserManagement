package com.mansa.user.Services.RoleService;


import com.mansa.user.Dtos.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto add(RoleDto roleDto);
    RoleDto getById(String id);
    RoleDto update(RoleDto roleDto);
    List<RoleDto> all();
    String delete(String id);
    RoleDto getByRole(String role);
}
