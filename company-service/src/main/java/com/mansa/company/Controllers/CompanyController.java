package com.mansa.company.Controllers;

import com.mansa.company.Dtos.CompanyDto;
import com.mansa.company.Services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Company")
public class CompanyController {

    private final CompanyService companyService;


    @PostMapping
    public ResponseEntity<CompanyDto> add(@RequestBody CompanyDto companyDto){
        return new ResponseEntity<>(companyService.add(companyDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> all(){
        return new ResponseEntity<>(companyService.all(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> companyById(@PathVariable String id){
        return new ResponseEntity<>(companyService.companyById(id),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CompanyDto> update(@RequestBody CompanyDto companyDto){
        return new ResponseEntity<>(companyService.update(companyDto), HttpStatus.CREATED);
    }
}
