package com.mansa.subscription.Services;

import com.mansa.subscription.Dtos.SubscriptionDto;
import com.mansa.subscription.Entities.Subscription;
import com.mansa.subscription.Enums.Status;
import com.mansa.subscription.Exceptions.SubscriptionNotFound;
import com.mansa.subscription.FeignClient.UserFeign;
import com.mansa.subscription.Mappers.SubscriptionMapper;
import com.mansa.subscription.Repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriptionServiceImp implements SubscriptionService{

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper mapper;
    private final UserFeign userFeign;

    @Override
    public SubscriptionDto add(SubscriptionDto subscriptionDto) {
        Subscription subscription = mapper.toEntity(subscriptionDto);
        subscription.setId(UUID.randomUUID().toString());
        subscription.setStatus(Status.PENDING);
        subscription.setUserId(userFeign.userById(
                subscriptionDto.getUserDto().getId())
                .getBody().getId()
        );
        return mapper.toDto(subscriptionRepository.save(subscription));
    }

    @Override
    public SubscriptionDto update(SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionById(subscriptionDto.getId());

        return null;
    }

    Subscription subscriptionById(String id){
        return subscriptionRepository.findById(id).orElseThrow(
                () -> new SubscriptionNotFound(id)
        );
    }

    @Override
    public String delete(String id) {
        subscriptionRepository.delete(subscriptionById(id));
        return "Subscription was deleted successfully";
    }

    @Override
    public SubscriptionDto getById(String id) {
        SubscriptionDto subscriptionDto= mapper.toDto(subscriptionById(id));
        log.info("user id {}",userFeign.userById(subscriptionById(id).getUserId()).getBody().getId());
        subscriptionDto.setUserDto(userFeign.userById(subscriptionById(id).getUserId()).getBody());
        return subscriptionDto;
    }

    @Override
    public List<SubscriptionDto> subscriptions() {
        return mapper.toDto(subscriptionRepository.findAll()).stream()
                .map(
                        subscriptionDto -> getById(subscriptionDto.getId())
                )
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionDto approveRequest(String id) {
        Subscription subscription = subscriptionById(id);
        subscription.setStatus(Status.APPROVED);
        return mapper.toDto(subscriptionRepository.save(subscription));
    }

    @Override
    public SubscriptionDto rejectRequest(String id) {
        Subscription subscription = subscriptionById(id);
        subscription.setStatus(Status.REJECTED);
        return mapper.toDto(subscriptionRepository.save(subscription));
    }
}
