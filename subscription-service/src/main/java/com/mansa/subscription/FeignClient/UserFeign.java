package com.mansa.subscription.FeignClient;

import com.mansa.subscription.Dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", fallbackFactory = UserFallBack.class)
public interface UserFeign {

    @GetMapping("/api/users/{id}")
    ResponseEntity<UserDto> userById(@PathVariable String id);

}
