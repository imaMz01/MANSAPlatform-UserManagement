package com.mansa.authorization.Service;

import com.mansa.authorization.Dto.RoleDto;
import com.mansa.authorization.Entity.Role;
import com.mansa.authorization.Exceptions.RoleAlreadyExistException;
import com.mansa.authorization.Exceptions.RoleNotFoundException;
import com.mansa.authorization.Mapper.RoleMapper;
import com.mansa.authorization.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public RoleDto add(RoleDto roleDto) {
        if(roleRepository.findByRole(roleDto.getRole()).isPresent()){
            throw new RoleAlreadyExistException(roleDto.getRole());
        }
        Role role = RoleMapper.roleMapper.toEntity(roleDto);
        role.setId(UUID.randomUUID().toString());
        return RoleMapper.roleMapper.toDto(
                roleRepository.save(role)
        );
    }

    @Override
    public RoleDto getById(String id) {
        return RoleMapper.roleMapper.toDto(getRole(id));
    }

    public Role getRole(String id){
        return roleRepository.findById(id).orElseThrow(
                RoleNotFoundException::new
        );
    }

    @Override
    public RoleDto update(RoleDto roleDto) {
        Role role = getRole(roleDto.getId());
        role.setRole(roleDto.getRole());
        return RoleMapper.roleMapper.toDto(
                roleRepository.save(role)
        );
    }

    @Override
    public List<RoleDto> all() {
        return RoleMapper.roleMapper.toDto(
                roleRepository.findAll()
        );
    }

    @Override
    public String delete(String id) {
        roleRepository.delete(getRole(id));
        return "Role deleted successfully";
    }

    @Override
    public RoleDto getByRole(String role) {
        return RoleMapper.roleMapper.toDto(
                roleRepository.findByRole(role).orElseThrow(
                        RoleNotFoundException::new
                )
        );
    }


}
