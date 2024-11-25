package com.mansa.notification.Dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailVerificationRequest {

    private String lastName;
    private String email;
    private String token;
}
