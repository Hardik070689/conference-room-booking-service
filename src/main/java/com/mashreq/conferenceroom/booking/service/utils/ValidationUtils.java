package com.mashreq.conferenceroom.booking.service.utils;


import com.mashreq.conferenceroom.booking.dto.BookingRequestDto;
import com.mashreq.conferenceroom.booking.exceptions.BookingExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.mashreq.conferenceroom.booking.exceptions.enums.ExceptionCodes.*;

@Slf4j
@Service
public class ValidationUtils {
    @Value("${app.minimumBookingInterval}")
    Integer bookingInterval;

    public void validateBookingRequestDto(BookingRequestDto bookingRequestDto){

        LocalTime startTime = bookingRequestDto.startTime().toLocalTime();
        LocalTime endTime = bookingRequestDto.endTime().toLocalTime();

        if(afterCurrentDate(bookingRequestDto.startTime().toLocalDate()) ||
                afterCurrentDate(bookingRequestDto.endTime().toLocalDate())){
            throw new BookingExceptions(BOOKING_IN_FUTURE_NOT_PERMITTED ,
                    bookingRequestDto.startTime() ,
                    bookingRequestDto.endTime() );
        }

        if(beforeCurrentTime(startTime) ||
                beforeCurrentTime(endTime))
            throw new BookingExceptions(BOOKING_TIMES_REQUESTED_IN_PAST ,
                    bookingRequestDto.startTime() , bookingRequestDto.endTime());

        if(endTime.isBefore(startTime))
            throw new BookingExceptions(BOOKING_ENDTIME_IS_BEFORE_STARTTIME ,
                    bookingRequestDto.endTime() , bookingRequestDto.startTime());

       Duration timeDifference =  Duration.between(startTime , endTime);
      long timeDiffInMinutes =  timeDifference.toMinutes();

      if(timeDiffInMinutes == 0){
          throw  new BookingExceptions(BOOKING_START_END_TIME_EQUALS  ,
                  bookingRequestDto.startTime() , bookingRequestDto.endTime());
      }


      int remainDiff  = Math.toIntExact((timeDiffInMinutes % bookingInterval));

      if(remainDiff > 0){
          throw new BookingExceptions(BOOKING_TIME_INTERVAL_CRITERIA_UNMET ,
                  bookingRequestDto.startTime() , bookingRequestDto.endTime() ,
                  bookingInterval);
      }
    }

    private boolean afterCurrentDate(LocalDate localDate){
        return localDate.isAfter(LocalDate.now());
    }


    private boolean beforeCurrentTime(LocalTime inputTime){
        return  inputTime.isBefore(LocalTime.now());
    }

}
