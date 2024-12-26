package com.mansa.user.FeignClient.DataFeign;

import com.mansa.user.Dtos.DataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "company-service", fallbackFactory = DataFallBack.class)
public interface DataFeign {
    @PutMapping("Company/data/assignCheckerToData/{idData}/{idChecker}")
    ResponseEntity<String> assignCheckerToData(@PathVariable String idData,
                                                @PathVariable String idChecker,
                                                @RequestHeader("AUTHORIZATION") String token);

    @GetMapping("Company/data/{id}")
    ResponseEntity<DataDto> dataById(@PathVariable String id);
}
