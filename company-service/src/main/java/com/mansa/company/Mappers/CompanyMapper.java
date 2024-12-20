package com.mansa.company.Mappers;

import com.mansa.company.Dtos.CompanyDto;
import com.mansa.company.Entities.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {


    CompanyDto toDto(Company user);
    Company toEntity(CompanyDto userDto);
    List<CompanyDto> toDto(List<Company> user);
}