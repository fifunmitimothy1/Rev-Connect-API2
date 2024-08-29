package com.rev_connect_api.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class TimestampUtil {

    public Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
}
