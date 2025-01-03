package com.mansa.user.Dtos;

import com.mansa.user.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SubscriptionDto {

    private String id;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
