package com.mansa.company.Services.DataService;

import com.mansa.company.Dtos.DataDto;

import java.util.List;

public interface DataService {
    DataDto add(DataDto dataDto);
    List<DataDto> all();
    List<DataDto> publishedData();
    DataDto checkData(String id);
    DataDto publishData(String id);
    DataDto dataById(String id);
    DataDto update(DataDto dataDto);
    List<DataDto> dataCompany(String name);
    String assignCheckerToData(String idData, String idChecker);
}
