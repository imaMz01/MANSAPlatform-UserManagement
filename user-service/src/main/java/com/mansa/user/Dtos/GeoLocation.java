package com.mansa.user.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocation {

    private String status;
    private String city;
    private String regionName;
    private String country;
}
