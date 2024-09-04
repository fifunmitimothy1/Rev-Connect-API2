package com.rev_connect_api.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimestampUtil {

    public LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now();
    }
}
