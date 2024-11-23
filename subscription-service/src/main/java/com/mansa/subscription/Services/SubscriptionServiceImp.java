package com.mansa.subscription.Services;

import com.mansa.subscription.Dtos.EmailRequest;
import com.mansa.subscription.Dtos.SubscriptionDto;
import com.mansa.subscription.Dtos.UserDto;
import com.mansa.subscription.Entities.Subscription;
import com.mansa.subscription.Enums.Status;
import com.mansa.subscription.Exceptions.SubscriptionNotFound;
import com.mansa.subscription.FeignClient.UserFeign;
import com.mansa.subscription.Mappers.SubscriptionMapper;
import com.mansa.subscription.Repositories.SubscriptionRepository;
import com.mansa.subscription.Utils.Statics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final StreamBridge streamBridge;

    @Override
    public SubscriptionDto add(SubscriptionDto subscriptionDto) {
        Subscription subscription = mapper.toEntity(subscriptionDto);
        subscription.setId(UUID.randomUUID().toString());
        subscription.setStatus(Status.PENDING);
        subscription.setUserId(userFeign.getCurrentUser().getBody().getId());
        SubscriptionDto subscriptionDto1= mapper.toDto(subscriptionRepository.save(subscription));
        subscriptionDto1.setUserDto(getById(subscriptionDto1.getId()).getUserDto());
        return subscriptionDto1;
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
    @Transactional
    public SubscriptionDto approveRequest(String id) {
        Subscription subscription = subscriptionById(id);
        subscription.setStatus(Status.APPROVED);
        SubscriptionDto subscriptionDto1= mapper.toDto(subscriptionRepository.save(subscription));
        subscriptionDto1.setUserDto(getById(subscriptionDto1.getId()).getUserDto());
        userFeign.addAuthority(subscriptionDto1.getUserDto().getId(), Statics.SUBSCRIBER_ROLE);
        userFeign.removeAuthority(subscriptionDto1.getUserDto().getId(), Statics.DEFAULT_ROLE);
        EmailRequest emailRequest = new EmailRequest(subscriptionDto1.getUserDto().getEmail(),
                subscriptionDto1.getUserDto().getLastName(),
                subscriptionDto1.getStatus().toString());
        log.info("detail : {}",emailRequest.toString());
        streamBridge.send("notification-topic",emailRequest);
        return subscriptionDto1;
    }

    @Override
    @Transactional
    public SubscriptionDto rejectRequest(String id) {
        Subscription subscription = subscriptionById(id);
        subscription.setStatus(Status.REJECTED);
        SubscriptionDto subscriptionDto1= mapper.toDto(subscriptionRepository.save(subscription));
        subscriptionDto1.setUserDto(getById(subscriptionDto1.getId()).getUserDto());
        EmailRequest emailRequest = new EmailRequest(subscriptionDto1.getUserDto().getEmail(),
                subscriptionDto1.getUserDto().getLastName(),
                subscriptionDto1.getStatus().toString());
        streamBridge.send("notification-topic",emailRequest);
        return subscriptionDto1;
    }

    @Override
    public List<SubscriptionDto> userSubscriptions(String id) {
        return mapper.toDto(subscriptionRepository.findByUserId(id));
    }

}
