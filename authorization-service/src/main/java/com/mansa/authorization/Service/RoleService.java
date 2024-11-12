package com.mansa.authorization.Service;

import com.mansa.authorization.Dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto add(RoleDto roleDto);
    RoleDto getById(String id);
    RoleDto update(RoleDto roleDto);
    List<RoleDto> all();
    String delete(String id);
}
