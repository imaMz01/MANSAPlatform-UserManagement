package com.mansa.company.FeignClient;

import com.mansa.company.Dtos.UserDto;
import com.mansa.company.Exceptions.FailedToFindService;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeoutException;

@Component
public class UserFallBack implements FallbackFactory<UserFeign> {
    @Override
    public UserFeign create(Throwable cause) {
        return new UserFeign() {
            @Override
            public ResponseEntity<UserDto> userById(String id) {
                if(cause instanceof FeignException.ServiceUnavailable ||
                        cause instanceof TimeoutException)
                    throw new FailedToFindService();
                throw new RuntimeException(cause);
            }
            @Override
            public ResponseEntity<UserDto> getCurrentUser(){
                if(cause instanceof FeignException.ServiceUnavailable ||
                        cause instanceof TimeoutException)
                    throw new FailedToFindService();
                throw new RuntimeException(cause);
            }
        };
    }
}
