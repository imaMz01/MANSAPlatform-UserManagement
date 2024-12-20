package com.mansa.user.Dtos;

import com.mansa.user.Entities.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogDto {

    private String action;
    private LocalDateTime timestamp;
    private String details;
    private List<String> roles;
    private String email;
}
