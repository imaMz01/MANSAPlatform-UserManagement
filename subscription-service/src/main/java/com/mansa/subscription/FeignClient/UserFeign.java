package com.mansa.subscription.FeignClient;

import com.mansa.subscription.Dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "user-service", fallbackFactory = UserFallBack.class)
public interface UserFeign {

    @GetMapping("/api/users/{id}")
    ResponseEntity<UserDto> userById(@PathVariable String id);

    @GetMapping("/api/users/me")
    ResponseEntity<UserDto> getCurrentUser();

    @PutMapping("/api/admin/addAuthority/{id}/{role}")
    public ResponseEntity<UserDto> addAuthority(@PathVariable String id, @PathVariable String role);

    @PutMapping("/api/admin/removeAuthority/{id}/{role}")
    public ResponseEntity<UserDto> removeAuthority(@PathVariable String id, @PathVariable String role);

}
