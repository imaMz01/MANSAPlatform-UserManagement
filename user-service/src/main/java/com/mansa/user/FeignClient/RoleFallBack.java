package com.mansa.user.FeignClient;

import com.mansa.user.Dtos.RoleDto;
import com.mansa.user.Exceptions.FailedToFindService;
import com.mansa.user.Exceptions.RoleNotFoundException;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleFallBack implements FallbackFactory<RoleFeignClient> {
    @Override
    public RoleFeignClient create(Throwable cause) {
        return new RoleFeignClient() {
            @Override
            public ResponseEntity<RoleDto> getByRole(String role) {
                if(cause instanceof FeignException.ServiceUnavailable)
                    throw new FailedToFindService();
                throw new  RuntimeException(cause);
            }
        };
    }
}
