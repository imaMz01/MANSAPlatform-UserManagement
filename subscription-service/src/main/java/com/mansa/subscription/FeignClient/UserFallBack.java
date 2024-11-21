package com.mansa.subscription.FeignClient;

import com.mansa.subscription.Dtos.UserDto;
import com.mansa.subscription.Exceptions.FailedToFindService;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFallBack implements FallbackFactory<UserFeign> {
    @Override
    public UserFeign create(Throwable cause) {
        return new UserFeign() {
            @Override
            public ResponseEntity<UserDto> userById(String id) {
                if(cause instanceof FeignException.ServiceUnavailable)
                    throw new FailedToFindService();
                throw new RuntimeException(cause);
            }
        };
    }
}
