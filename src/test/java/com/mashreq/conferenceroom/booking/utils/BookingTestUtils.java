package com.mashreq.conferenceroom.booking.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class BookingTestUtils {

    public static LocalDateTime getTodayLocalDateTime(LocalTime localTime){
        return LocalDateTime.now()
                .withHour(localTime.getHour())
                .withMinute(localTime.getMinute());
    }

    public static LocalDateTime getLocalDateTime(LocalDateTime localDateTime , LocalTime localTime){
        return localDateTime.withMinute(localTime.getMinute())
                .withHour(localTime.getHour());
    }

}
