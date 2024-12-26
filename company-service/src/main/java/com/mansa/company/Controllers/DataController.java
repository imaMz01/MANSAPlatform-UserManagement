package com.mansa.company.Controllers;

import com.mansa.company.Dtos.DataDto;
import com.mansa.company.Services.DataService.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("Company/data")
@Slf4j
public class DataController {

    private final DataService dataService;

    @PreAuthorize("hasRole(@Statics.MAKER_ROLE)")
    @PostMapping
    public ResponseEntity<DataDto> add(@RequestBody DataDto dataDto){
        return new ResponseEntity<>(dataService.add(dataDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/all")
    public ResponseEntity<List<DataDto>> all(){
        return new ResponseEntity<>(dataService.all(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.SUBSCRIBER_ROLE)")
    @GetMapping("/published")
    public ResponseEntity<List<DataDto>> published(){
        return new ResponseEntity<>(dataService.publishedData(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.CHECKER_ROLE)")
    @PutMapping("/check/{id}")
    public ResponseEntity<DataDto> checkData(@PathVariable String id){
        return new ResponseEntity<>(dataService.checkData(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PutMapping("/publish/{id}")
    public ResponseEntity<DataDto> publishData(@PathVariable String id){
        return new ResponseEntity<>(dataService.publishData(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.MAKER_ROLE)")
    @PutMapping("/update")
    public ResponseEntity<DataDto> updateData(@RequestBody DataDto dataDto){
        return new ResponseEntity<>(dataService.update(dataDto),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.MAKER_ROLE) or hasRole(@Statics.ADMIN_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE) or hasRole(@Statics.CHECKER_ROLE)")
    @GetMapping("/{id}")
    public ResponseEntity<DataDto> dataById(@PathVariable String id){
        return new ResponseEntity<>(dataService.dataById(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE) or hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/dataCompany/{name}")
    public ResponseEntity<List<DataDto>> dataCompany(@PathVariable String name){
        return new ResponseEntity<>(dataService.dataCompany(name),HttpStatus.OK);
    }

    @PutMapping("/assignCheckerToData/{idData}/{idChecker}")
    public ResponseEntity<String> assignCheckerToData(@PathVariable String idData, @PathVariable String idChecker){
        log.info("hiii we are here in company");
        return new ResponseEntity<>(dataService.assignCheckerToData(idData,idChecker),HttpStatus.OK);
    }
}
