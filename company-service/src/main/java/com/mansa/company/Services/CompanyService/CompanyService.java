package com.mansa.company.Services.CompanyService;

import com.mansa.company.Dtos.CompanyDto;
import com.mansa.company.Dtos.UserDto;

import java.util.List;

public interface CompanyService {

    CompanyDto add(CompanyDto companyDto);
    List<CompanyDto> all();
    CompanyDto update(CompanyDto companyDto);
    CompanyDto companyById(String id);
    List<UserDto> usersByCompany(String name);
    CompanyDto companyByName(String name);
}
