package com.mansa.notification.Dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    private String email;
    private String password;
}