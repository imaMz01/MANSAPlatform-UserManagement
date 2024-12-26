package com.mansa.user.Dtos;

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
public class DataDto {

    private String id;
    @NotNull(message = "Description should be not null")
    @NotEmpty(message = "Description should be not empty")
    private String description;
    private UserDto maker;
    private UserDto checker;
    private UserDto publisher;
    private boolean checked;
    private boolean published;

}
