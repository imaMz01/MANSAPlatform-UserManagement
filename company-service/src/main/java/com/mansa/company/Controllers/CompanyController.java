package com.mansa.company.Controllers;

import com.mansa.company.Dtos.CompanyDto;
import com.mansa.company.Dtos.UserDto;
import com.mansa.company.Services.CompanyService.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Company")
public class CompanyController {

    private final CompanyService companyService;

    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE)")
    @PostMapping
    public ResponseEntity<CompanyDto> add(@RequestBody CompanyDto companyDto){
        return new ResponseEntity<>(companyService.add(companyDto), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE)")
    @GetMapping
    public ResponseEntity<List<CompanyDto>> all(){
        return new ResponseEntity<>(companyService.all(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE)")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> companyById(@PathVariable String id){
        return new ResponseEntity<>(companyService.companyById(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE)")
    @PutMapping
    public ResponseEntity<CompanyDto> update(@RequestBody CompanyDto companyDto){
        return new ResponseEntity<>(companyService.update(companyDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE)")
    @GetMapping("/companyName/{name}")
    public ResponseEntity<List<UserDto>> usersByCompanyName(@PathVariable String name){
        return new ResponseEntity<>(companyService.usersByCompany(name),HttpStatus.OK);
    }
}
