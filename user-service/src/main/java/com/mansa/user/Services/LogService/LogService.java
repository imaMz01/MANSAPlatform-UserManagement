package com.mansa.user.Services.LogService;


import com.mansa.user.Dtos.LogDto;
import com.mansa.user.Entities.Log;

import java.util.List;

public interface LogService {

    void add(Log log);
    List<LogDto> all();
}
