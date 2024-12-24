package com.mansa.company.Dtos;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserDto {

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
    private String companyName;
}
