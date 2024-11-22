package com.mansa.subscription.Dtos;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    private String email;
    private String lastName;
    private String status;
}
