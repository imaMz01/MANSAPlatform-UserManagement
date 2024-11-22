package com.mansa.notification.Dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailRequest {

    private String email;
    private String lastName;
    private String status;
}