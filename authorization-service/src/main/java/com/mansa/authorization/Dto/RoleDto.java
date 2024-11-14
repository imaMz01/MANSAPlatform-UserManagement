package com.mansa.authorization.Dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private String id;
    @NotNull(message = "Role should be not null")
    @NotEmpty(message = "Role should be not empty")
    private String role;
}
