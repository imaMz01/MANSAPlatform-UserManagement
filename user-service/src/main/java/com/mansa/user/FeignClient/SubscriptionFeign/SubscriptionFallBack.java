package com.mansa.user.FeignClient.SubscriptionFeign;

import com.mansa.user.Dtos.SubscriptionDto;
import com.mansa.user.Exceptions.FailedToFindService;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public class SubscriptionFallBack implements FallbackFactory<SubscriptionFeign> {
    @Override
    public SubscriptionFeign create(Throwable cause) {
        return new SubscriptionFeign() {
            @Override
            public ResponseEntity<List<SubscriptionDto>> userSubscriptions(String id) {
                if(cause instanceof FeignException.ServiceUnavailable ||
                        cause instanceof TimeoutException)
                    throw new FailedToFindService();
                throw new RuntimeException(cause);
            }
        };
    }
}
