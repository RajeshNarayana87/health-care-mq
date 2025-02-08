package com.healnet.healthcare.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {

    public static Long getCurrentTimeInEpoch(){
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
