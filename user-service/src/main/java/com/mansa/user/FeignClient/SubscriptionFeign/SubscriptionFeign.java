package com.mansa.user.FeignClient.SubscriptionFeign;

import com.mansa.user.Dtos.SubscriptionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "subscription-service", fallbackFactory = SubscriptionFallBack.class)
public interface SubscriptionFeign {

    @GetMapping("/Subscription/userSubscriptions/{id}")
    ResponseEntity<List<SubscriptionDto>> userSubscriptions(@PathVariable String id);
}
