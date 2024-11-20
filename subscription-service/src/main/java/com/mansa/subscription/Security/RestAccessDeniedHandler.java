package com.mansa.subscription.Security;

import com.mansa.subscription.Dtos.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        setErrorResponse(HttpServletResponse.SC_FORBIDDEN, response, accessDeniedException);

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