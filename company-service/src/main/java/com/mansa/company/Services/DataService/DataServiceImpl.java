package com.mansa.company.Services.DataService;

import com.mansa.company.Dtos.CompanyDto;
import com.mansa.company.Dtos.DataDto;
import com.mansa.company.Dtos.UserDto;
import com.mansa.company.Entities.Company;
import com.mansa.company.Entities.Data;
import com.mansa.company.Exceptions.CheckerAndMakerAreIdenticalException;
import com.mansa.company.Exceptions.DataNotFoundException;
import com.mansa.company.FeignClient.UserFeign;
import com.mansa.company.Mappers.CompanyMapper;
import com.mansa.company.Mappers.DataMapper;
import com.mansa.company.Repositories.DataRepository;
import com.mansa.company.Services.CompanyService.CompanyService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final DataRepository dataRepository;
    private final DataMapper dataMapper;
    private final UserFeign userFeign;
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @Override
    public DataDto add(DataDto dataDto) {
        UserDto maker = userFeign.getCurrentUser().getBody();
        if(maker == null){
            return null;
        }
        Data data = dataMapper.toEntity(dataDto);
        data.setId(UUID.randomUUID().toString());
        data.setIdMaker(maker.getId());
        data.setCompany(companyMapper.toEntity(companyService.companyByName(maker.getCompanyName())));
        return getDataDto(dataRepository.save(data));
    }

    @Override
    public List<DataDto> all() {
        return dataRepository.findAll().stream()
                .map(this::getDataDto)
                .toList();
    }

    @NotNull
    private DataDto getDataDto(Data data) {
        CompanyDto companyDto = companyMapper.toDto(data.getCompany());
        companyDto.setCreatedBy(userFeign.userById(data.getCompany().getUserId()).getBody());
        return new DataDto(data.getId(), data.getDescription(),companyDto ,
                data.getIdMaker() != null ? userFeign.userById(data.getIdMaker()).getBody() : new UserDto(),
                data.getIdChecker() != null ? userFeign.userById(data.getIdChecker()).getBody() : new UserDto(),
                data.getIdAdmin() != null ? userFeign.userById(data.getIdAdmin()).getBody() : new UserDto(),
                data.isChecked(), data.isPublished());
    }

    @Override
    public List<DataDto> publishedData() {
        return dataRepository.findAll().stream()
                .filter(Data::isPublished)
                .map(data -> new DataDto(data.getId(),data.getDescription(),
                        companyMapper.toDto(data.getCompany()),
                        userFeign.userById(data.getIdMaker()).getBody(),
                        userFeign.userById(data.getIdChecker()).getBody(),
                        userFeign.userById(data.getIdAdmin()).getBody(),
                        data.isChecked(),data.isPublished()))
                .toList();
    }

    public Data getById(String id){
        return dataRepository.findById(id).orElseThrow(()-> new DataNotFoundException(id));
    }

    @Override
    public DataDto checkData(String id) {
        UserDto checker = userFeign.getCurrentUser().getBody();
        if(checker == null){
            return null;
        }
        Data data = getById(id);
        if(checker.getId().equals(data.getIdMaker()))
            throw new CheckerAndMakerAreIdenticalException();
        data.setIdChecker(checker.getId());
        data.setChecked(true);
        return getDataDto(dataRepository.save(data));
    }

    @Override
    public DataDto publishData(String id) {
        UserDto publisher = userFeign.getCurrentUser().getBody();
        if(publisher == null){
            return null;
        }
        Data data = getById(id);
        data.setIdAdmin(publisher.getId());
        data.setPublished(true);
        return getDataDto(dataRepository.save(data));
    }

    @Override
    public DataDto dataById(String id) {
        return getDataDto(getById(id));
    }

    @Override
    public DataDto update(DataDto dataDto) {
        Data data = getById(dataDto.getId());
        data.setDescription(dataDto.getDescription());
        return getDataDto(dataRepository.save(data));
    }
}
