package com.mansa.company.Services.CompanyService;


import com.mansa.company.Dtos.CompanyDto;
import com.mansa.company.Dtos.UserDto;
import com.mansa.company.Entities.Company;
import com.mansa.company.Exceptions.CompanyAlreadyExistException;
import com.mansa.company.Exceptions.CompanyNotFountException;
import com.mansa.company.FeignClient.UserFeign;
import com.mansa.company.Mappers.CompanyMapper;
import com.mansa.company.Repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImp implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserFeign userFeign;

    @Override
    public CompanyDto add(CompanyDto companyDto) {
        if(companyRepository.findByNameIgnoreCase(companyDto.getName()).isPresent())
            throw new CompanyAlreadyExistException(companyDto.getName());
        Company company =companyMapper.toEntity(companyDto);
        company.setId(UUID.randomUUID().toString());
        company.setUserId(userFeign.getCurrentUser().getBody().getId());
        return getCompanyDto(companyRepository.save(company));
    }


    private CompanyDto getCompanyDto(Company company) {
        CompanyDto savedDto = companyMapper.toDto(company);
        savedDto.setCreatedBy(userFeign.userById(company.getUserId()).getBody());
        return savedDto;
    }

    @Override
    public List<CompanyDto> all() {
        return companyRepository.findAll()
                .stream()
                .map(this::getCompanyDto)
                .toList();
    }

    @Override
    public CompanyDto update(CompanyDto companyDto) {
        Company company = getById(companyDto.getId());
        if(!company.getName().equals(companyDto.getName())
                && companyRepository.findByNameIgnoreCase(companyDto.getName()).isPresent())
            throw new CompanyAlreadyExistException(companyDto.getName());
        company.setName(companyDto.getName());
        company.setDescription(companyDto.getDescription());
        company.setEmail(companyDto.getEmail());
        company.setCa(companyDto.getCa());
        company.setAddress(companyDto.getAddress());
        company.setSector(companyDto.getSector());
        company.setType(companyDto.getType());
        company.setCreationDate(companyDto.getCreationDate());
        company.setTel(companyDto.getTel());
        return getCompanyDto(companyRepository.save(company));
    }

    public Company getById(String id) {
        return companyRepository.findById(id).orElseThrow(()-> new CompanyNotFountException("id "+id));
    }


    @Override
    public CompanyDto companyById(String id) {
        return getCompanyDto(getById(id));
    }

    @Override
    public List<UserDto> usersByCompany(String name) {
        return userFeign.usersCompany(name).getBody();
    }

    @Override
    public CompanyDto companyByName(String name) {
        return getCompanyDto(companyRepository.findByNameIgnoreCase(name).orElseThrow(()-> new CompanyNotFountException("name "+name)));
    }
}
