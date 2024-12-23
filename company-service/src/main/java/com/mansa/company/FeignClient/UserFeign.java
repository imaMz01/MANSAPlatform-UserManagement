package com.mansa.company.FeignClient;

import com.mansa.company.Dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service", fallbackFactory = UserFallBack.class)
public interface UserFeign {

    @GetMapping("/api/users/{id}")
    ResponseEntity<UserDto> userById(@PathVariable String id);

    @GetMapping("/api/users/me")
    ResponseEntity<UserDto> getCurrentUser();

    @GetMapping("/api/usersCompany/{name}")
    ResponseEntity<List<UserDto>> usersCompany(@PathVariable String name);
}
