package com.mansa.user.FeignClient;

import com.mansa.user.Dtos.RoleDto;
import com.mansa.user.Exceptions.FailedToFindService;
import com.mansa.user.Exceptions.RoleNotFoundException;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;

public class RoleFallBack implements FallbackFactory<RoleFeignClient> {
    @Override
    public RoleFeignClient create(Throwable cause) {
        return new RoleFeignClient() {
            @Override
            public ResponseEntity<RoleDto> getByRole(String role) {
                if (cause instanceof FeignException.NotFound) {
                    // Si l'erreur Feign est de type 404 (rôle non trouvé), on renvoie une exception spécifique
                    throw new RoleNotFoundException(role);
                }
                throw new FailedToFindService();
            }
        };
    }
}
