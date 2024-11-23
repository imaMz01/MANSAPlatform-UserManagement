package com.mansa.subscription.Services;

import com.mansa.subscription.Dtos.SubscriptionDto;
import com.mansa.subscription.Dtos.UserDto;

import java.util.List;

public interface SubscriptionService {

    SubscriptionDto add(SubscriptionDto subscriptionDto);
    SubscriptionDto update(SubscriptionDto subscriptionDto);
    String delete(String id);
    SubscriptionDto getById(String id);
    List<SubscriptionDto> subscriptions();
    SubscriptionDto approveRequest(String id);
    SubscriptionDto rejectRequest(String id);
    List<SubscriptionDto> userSubscriptions(String id);

}
