package com.mashreq.conferenceroom.booking.exceptions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ExceptionCodes {

    BOOKING_IN_FUTURE_NOT_PERMITTED("BOOKING_EXCEPTION_007" , "Booking request start date :(%s) or end date:(%s) is in future" , HttpStatus.BAD_REQUEST),

    BOOKING_COLLIDES_WITH_MAINTENANCE_TIMES("BOOKING_EXCEPTION_001" , "Conference Room : (%s) of capacity : (%s) persons unavailable since it collides with maintenance times"  ,
            HttpStatus.BAD_REQUEST),
    BOOKING_COLLIDES_WITH_EXISTING_BOOKING("BOOKING_EXCEPTION_002" , "No Conference rooms available for booking for the requested range : (%s) - (%s)" , HttpStatus.BAD_REQUEST),
    BOOKING_TIMES_REQUESTED_IN_PAST("BOOKING_EXCEPTION_003" , "Requested booking time range : (%s) - (%s) should be after the current time" , HttpStatus.BAD_REQUEST),
    BOOKING_TIME_INTERVAL_CRITERIA_UNMET("BOOKING_EXCEPTION_004" , "Booking time interval (%s) - (%s) does not fall in multiple of (%s) minutes interval",HttpStatus.BAD_REQUEST),

    BOOKING_ENDTIME_IS_BEFORE_STARTTIME("BOOKING_EXCEPTION_005" , "Booking End Time : (%s) is before the start time : (%s)" ,HttpStatus.BAD_REQUEST ),
    BOOKING_START_END_TIME_EQUALS("BOOKING_EXCEPTION_006" , "Booking start time : (%s) and endtime : (%s) should not be equal", HttpStatus.BAD_REQUEST);
    private final String errorCode ;
    private final String errorMessage;
    private final HttpStatus httpStatus;

}
