package com.mansa.user.EntityTest;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTest {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String tel;
    private String email;
    private String password;
    private String address;
    private boolean enabled;
    private boolean emailVerified;
    private LocalDateTime created;
    private LocalDateTime updated;
}
