package com.mansa.user.Controllers;

import com.mansa.user.Annotations.RequestLogger;
import com.mansa.user.Dtos.LogDto;
import com.mansa.user.Services.LogService.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Audit")
public class LogController {

    private final LogService logService;

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @RequestLogger(action = "all logs")
    @GetMapping
    public ResponseEntity<List<LogDto>> all(){
        return new ResponseEntity<>(logService.all(), HttpStatus.OK);
    }
}
