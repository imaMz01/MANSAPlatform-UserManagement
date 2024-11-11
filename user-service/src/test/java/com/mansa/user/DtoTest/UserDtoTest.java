package com.mansa.user.DtoTest;

import com.mansa.user.Annotations.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoTest {

    private String id;
    @NotNull(message = "FirstName should be not null")
    @NotEmpty(message = "FirstName should be not empty")
    private String firstName;
    @NotNull(message = "LastName should be not null")
    @NotEmpty(message = "LastName should be not empty")
    private String lastName;
    @Size(min = 10,max = 10, message = "Tel should have 10 number" )
    private String tel;
    @NotNull(message = "Email should be not null")
    @NotEmpty(message = "Email should be not empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Password should be not null")
    @Password
    private String password;
    @NotNull(message = "Email should be not null")
    @NotEmpty(message = "Email should be not empty")
    private String address;
    private boolean enabled;
    private boolean emailVerified;
    private LocalDateTime created;
    private LocalDateTime updated;

}
