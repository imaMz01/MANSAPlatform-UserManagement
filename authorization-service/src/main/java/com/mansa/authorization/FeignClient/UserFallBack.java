package com.mansa.authorization.FeignClient;

import com.mansa.authorization.Dto.UserDto;
import com.mansa.authorization.Exceptions.FailedToFindService;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFallBack implements FallbackFactory<UserFeignClient> {
    @Override
    public UserFeignClient create(Throwable cause) {
        return new UserFeignClient() {
            @Override
            public ResponseEntity<UserDto> addAuthority(String id, String role) {
                if(cause instanceof FeignException.ServiceUnavailable)
                    throw new  FailedToFindService();
                throw new RuntimeException(cause);
            }

            @Override
            public ResponseEntity<UserDto> removeAuthority(String id, String role) {
                if(cause instanceof FeignException.ServiceUnavailable)
                    throw new  FailedToFindService();
                throw new RuntimeException(cause);
            }
        };
    }
}
