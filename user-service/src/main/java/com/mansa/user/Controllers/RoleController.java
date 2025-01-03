package com.mansa.user.Controllers;

import com.mansa.user.Annotations.RequestLogger;
import com.mansa.user.Dtos.RoleDto;
import com.mansa.user.Services.RoleService.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authorization")
public class RoleController {

    private final RoleService roleService;

    @RequestLogger(action = "add role")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PostMapping
    public ResponseEntity<RoleDto> add(@Valid @RequestBody RoleDto roleDto){
        return new ResponseEntity<>(roleService.add(roleDto), HttpStatus.CREATED);
    }

    @RequestLogger(action = "all roles")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping
    public ResponseEntity<List<RoleDto>> roles(){
        return new ResponseEntity<>(roleService.all(),HttpStatus.OK);
    }


    @RequestLogger(action = "role by id")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("byId/{id}")
    public ResponseEntity<RoleDto> roleById(@PathVariable String id){
        return new ResponseEntity<>(roleService.getById(id),HttpStatus.OK);
    }

    @RequestLogger(action = "update role")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PutMapping
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto){
        return new ResponseEntity<>(roleService.update(roleDto),HttpStatus.OK);
    }

    @RequestLogger(action = "delete role")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        return new ResponseEntity<>(roleService.delete(id),HttpStatus.OK);
    }

    @RequestLogger(action = "get role by id")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("byRole/{role}")
    public ResponseEntity<RoleDto> getByRole(@PathVariable String role){
        return new ResponseEntity<>(roleService.getByRole(role),HttpStatus.OK);
    }

}
