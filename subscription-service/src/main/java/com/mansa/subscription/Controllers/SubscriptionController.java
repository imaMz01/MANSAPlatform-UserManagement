package com.mansa.subscription.Controllers;

import com.mansa.subscription.Dtos.SubscriptionDto;
import com.mansa.subscription.Services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PreAuthorize("hasRole('MANSA-GUEST-GR')")
    @PostMapping("/subscriptions/request")
    public ResponseEntity<SubscriptionDto> add(@RequestBody SubscriptionDto subscriptionDto){
        return new ResponseEntity<>(subscriptionService.add(subscriptionDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @DeleteMapping("/subscriptions/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        return new ResponseEntity<>(subscriptionService.delete(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/admin/subscriptions")
    public ResponseEntity<List<SubscriptionDto>> subscriptions(){
        return new ResponseEntity<>(subscriptionService.subscriptions(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/subscriptions/{id}")
    public ResponseEntity<SubscriptionDto> subscriptionById(@PathVariable String id){
        return new ResponseEntity<>(subscriptionService.getById(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PutMapping("/admin/subscriptions/{id}/approve")
    public ResponseEntity<SubscriptionDto> approveSubscription(@PathVariable String id){
        return new ResponseEntity<>(subscriptionService.approveRequest(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PutMapping("/admin/subscriptions/{id}/reject")
    public ResponseEntity<SubscriptionDto> rejectSubscription(@PathVariable String id){
        return new ResponseEntity<>(subscriptionService.rejectRequest(id),HttpStatus.OK);
    }
}
