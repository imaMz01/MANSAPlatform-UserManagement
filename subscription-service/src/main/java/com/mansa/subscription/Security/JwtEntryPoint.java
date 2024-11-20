package com.mansa.subscription.Security;

import com.mansa.subscription.Dtos.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authException) throws IOException {
        log.info(" unauthorized ");
        setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response,authException);
    }
    public void setErrorResponse(int status, HttpServletResponse response, Throwable ex){
        response.setStatus(status);
        response.setContentType("application/json");
        // A class used for errors
        ErrorResponse apiError = new ErrorResponse(status,ex.getMessage());
        try {
            String json = apiError.convertToJson();
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}