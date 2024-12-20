package com.mansa.user.Services.LogService;

import com.mansa.user.Dtos.LogDto;
import com.mansa.user.Entities.Log;
import com.mansa.user.Entities.Role;
import com.mansa.user.Mappers.LogMapper;
import com.mansa.user.Repositories.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogServiceImp implements LogService{

    private final LogRepository logRepository;
    private final LogMapper logMapper;

    @Override
    public void add(Log log) {
        log.setId(UUID.randomUUID().toString());
        logRepository.save(log);
    }

    @Override
    public List<LogDto> all() {
        return logRepository.findAll().stream()
                .map(log -> LogDto.builder()
                        .action(log.getAction())
                        .details(log.getDetails())
                        .timestamp(log.getTimestamp())
                        .email(log.getUser().getEmail())
                        .roles(log.getUser().getRoles().stream().map(Role::getRole).toList())
                        .build())
                .toList();
    }
}
