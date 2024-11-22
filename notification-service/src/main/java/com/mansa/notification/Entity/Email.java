package com.mansa.notification.Entity;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class Email {

    private String id;
    private String email;
    private LocalDateTime sentAt;
    private String subject;
    private String content;

}
