package com.mansa.company.Mappers;

import com.mansa.company.Dtos.DataDto;
import com.mansa.company.Entities.Data;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = CompanyMapper.class)
public interface DataMapper {

    DataDto toDto(Data data);
    Data toEntity(DataDto dataDto);
    List<DataDto> toDto(List<Data> data);
}
