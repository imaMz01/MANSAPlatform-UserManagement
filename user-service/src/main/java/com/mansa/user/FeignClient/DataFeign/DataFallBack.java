package com.mansa.user.FeignClient.DataFeign;

import com.mansa.user.Dtos.DataDto;
import com.mansa.user.Exceptions.FailedToFindService;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeoutException;

@Component
public class DataFallBack implements FallbackFactory <DataFeign>{
    @Override
    public DataFeign create(Throwable cause) {
        return new DataFeign() {
            @Override
            public ResponseEntity<String> assignCheckerToData(String idData, String idChecker,String authorizationHeader) {
                if(cause instanceof FeignException.ServiceUnavailable ||
                        cause instanceof TimeoutException)
                    throw new FailedToFindService();
                throw new RuntimeException(cause);
            }

            @Override
            public ResponseEntity<DataDto> dataById(String id) {
                if(cause instanceof FeignException.ServiceUnavailable ||
                        cause instanceof TimeoutException)
                    throw new FailedToFindService();
                throw new RuntimeException(cause);
            }
        };
    }
}
