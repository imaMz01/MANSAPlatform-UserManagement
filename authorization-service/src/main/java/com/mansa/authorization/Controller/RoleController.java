package com.mansa.authorization.Controller;

import com.mansa.authorization.Dto.RoleDto;
import com.mansa.authorization.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDto> add(@RequestBody RoleDto roleDto){
        return new ResponseEntity<>(roleService.add(roleDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> roles(){
        return new ResponseEntity<>(roleService.all(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> roleById(@PathVariable String id){
        return new ResponseEntity<>(roleService.getById(id),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto){
        return new ResponseEntity<>(roleService.update(roleDto),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        return new ResponseEntity<>(roleService.delete(id),HttpStatus.OK);
    }
}
