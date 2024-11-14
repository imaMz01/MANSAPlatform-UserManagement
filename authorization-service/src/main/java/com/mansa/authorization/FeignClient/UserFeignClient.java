package com.mansa.authorization.FeignClient;

import com.mansa.authorization.Dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "user-service", fallbackFactory = UserFallBack.class)
public interface UserFeignClient {

    @PutMapping("api/admin/addAuthority/{id}/{role}")
    ResponseEntity<UserDto> addAuthority(@PathVariable String id, @PathVariable String role);

    @PutMapping("api/admin/removeAuthority/{id}/{role}")
    ResponseEntity<UserDto> removeAuthority(@PathVariable String id, @PathVariable String role);
}
