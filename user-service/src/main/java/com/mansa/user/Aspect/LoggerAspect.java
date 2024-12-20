package com.mansa.user.Aspect;

import com.mansa.user.Annotations.RequestLogger;
import com.mansa.user.Dtos.GeoLocation;
import com.mansa.user.Dtos.JwtAuthenticationResponse;
import com.mansa.user.Dtos.UserDto;
import com.mansa.user.Entities.Log;
import com.mansa.user.Entities.User;
import com.mansa.user.Mappers.UserMapper;
import com.mansa.user.Services.LogService.LogService;
import com.mansa.user.Services.UserService.UserService;
import com.mansa.user.Util.GeoLocationService;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
@Aspect
public class LoggerAspect {

    private final LogService logService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final GeoLocationService geoLocationService;

    @Around("@annotation(requestLogger)")
    public Object logRequest(ProceedingJoinPoint joinPoint, RequestLogger requestLogger) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.debug("Aspect triggered for method: {}", joinPoint.getSignature().getName());
        log.info("{}: Request received", request.getRequestURI());
        log.info("action : {}",requestLogger.action());
        Object object = joinPoint.proceed();
        String ip = request.getHeader("X-Forwarded-For");
        ip = ip.split(",")[0].trim();
        GeoLocation response = geoLocationService.getAddress(ip);
        String address = response!= null ?response.getCountry()+","+response.getRegionName()+","+response.getCity() : "";
        User user = null;
        if(requestLogger.action().equals("login") || requestLogger.action().equals("register"))
            user = userMapper.toEntity(((ResponseEntity<JwtAuthenticationResponse>) object).getBody().getUserDto());
        else
            user = userMapper.toEntity(userService.getCurrentUser());
        logService.add(Log.builder()
                    .id("")
                    .action(requestLogger.action())
                    .details(Arrays.toString(joinPoint.getArgs()))
                    .timestamp(LocalDateTime.now())
                    .ipAddress(ip)
                    .address(address)
                    .user(user)
                    .build());
        log.info("{}: Request finished", request.getRequestURI());
        return object;

    }
}
