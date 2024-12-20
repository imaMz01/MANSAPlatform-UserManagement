package com.mansa.user.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Log {

    @Id
    private String id;
    @ManyToOne
    private User user;
    private String ipAddress;
    private String address;
    private String action;
    private LocalDateTime timestamp;
    private String details;
}
