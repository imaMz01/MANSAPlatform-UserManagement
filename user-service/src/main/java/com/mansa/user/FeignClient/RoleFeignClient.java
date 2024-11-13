package com.mansa.user.FeignClient;

import com.mansa.user.Dtos.RoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "authorization-service", fallbackFactory = RoleFallBack.class)
public interface RoleFeignClient {

    @GetMapping("/authorization/byRole/{role}")
    ResponseEntity<RoleDto> getByRole(@PathVariable String role);
}
