package com.mansa.subscription.Mappers;

import com.mansa.subscription.Dtos.SubscriptionDto;
import com.mansa.subscription.Entities.Subscription;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionDto toDto(Subscription role);
    Subscription toEntity(SubscriptionDto roleDto);
    List<SubscriptionDto> toDto(List<Subscription> role);
    List<Subscription> toEntity(List<SubscriptionDto> roleDto);
}
