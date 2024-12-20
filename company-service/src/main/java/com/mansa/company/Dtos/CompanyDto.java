package com.mansa.company.Dtos;

import com.mansa.company.Enums.Type;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyDto {

    private String id;
    @NotNull(message = "Name should be not null")
    @NotEmpty(message = "Name should be not empty")
    private String name;
    @NotNull(message = "Type should be not null")
    @NotEmpty(message = "Type should be not empty")
    private Type type;
    @NotNull(message = "Sector should be not null")
    @NotEmpty(message = "Sector should be not empty")
    private String sector;
    @NotNull(message = "Address should be not null")
    @NotEmpty(message = "Address should be not empty")
    private String address;
    @NotNull(message = "Description should be not null")
    @NotEmpty(message = "Description should be not empty")
    private String description;
    @Size(min = 10,max = 10, message = "Tel should have 10 number" )
    private String tel;
    @NotNull(message = "Email should be not null")
    @NotEmpty(message = "Email should be not empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Creation date should be not null")
    @NotEmpty(message = "Creation date should be not empty")
    private LocalDate creationDate;
    @NotNull(message = "CA should be not null")
    @NotEmpty(message = "CA should be not empty")
    private String ca;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto createdBy;
}
