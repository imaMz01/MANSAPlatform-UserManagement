package com.mansa.company.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mansa.company.Entities.Company;
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
    private String description;
    private CompanyDto company;
    private UserDto maker;
    private UserDto checker;
    private UserDto publisher;
    private boolean checked;
    private boolean published;

}
