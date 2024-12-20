package com.mansa.user.Util;

import com.mansa.user.Dtos.GeoLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GeoLocationService {

    private final static String GEOLOCATION_API_URL = "http://ip-api.com/json/";

    public String getAddress(String ip){
        RestTemplate restTemplate = new RestTemplate();
        String url = GEOLOCATION_API_URL + ip;
        try {
            GeoLocation response = restTemplate.getForObject(url, GeoLocation.class);
            return response!=null && response.getStatus().equals("success") ? response.getCountry()+","+response.getRegionName()+","+response.getCity() : "Unknown";
        }catch (Exception e){
            log.error("error :{}",e.getMessage());
            return "Unknown";
        }
    }
}
