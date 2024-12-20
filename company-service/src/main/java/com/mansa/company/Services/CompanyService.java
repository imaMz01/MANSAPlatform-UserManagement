package com.mansa.company.Services;

import com.mansa.company.Dtos.CompanyDto;

import java.util.List;

public interface CompanyService {

    CompanyDto add(CompanyDto companyDto);
    List<CompanyDto> all();
    CompanyDto update(CompanyDto companyDto);
    CompanyDto companyById(String id);

}
