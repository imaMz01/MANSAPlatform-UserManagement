package com.mansa.user.Util;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            final HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null) {
                // Log pour vérifier que le jeton est récupéré correctement
                System.out.println("Authorization Header: " + authorizationHeader);
                template.header(HttpHeaders.AUTHORIZATION, authorizationHeader);
            } else {
                // Log si l'en-tête Authorization est manquant
                System.out.println("Authorization header is missing.");
            }
            String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
            if (xForwardedFor != null) {
                // Log pour vérifier l'en-tête X-Forwarded-For
                System.out.println("X-Forwarded-For: " + xForwardedFor);
                template.header("X-Forwarded-For", xForwardedFor);
            } else {
                // Si l'en-tête X-Forwarded-For est manquant
//                xForwardedFor="41.141.135.113";
                System.out.println("X-Forwarded-For header is missing.");
            }
        }
    }
}