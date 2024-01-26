package com.mashreq.conferenceroom.booking.exceptions;

import com.mashreq.conferenceroom.booking.exceptions.enums.ExceptionCodes;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Slf4j
public class BookingExceptions extends RuntimeException{
    @Getter private HttpStatus httpStatus;
    @Getter private String exceptionCode ;
    public BookingExceptions(ExceptionCodes exceptionCodes){
        super(exceptionCodes.getErrorMessage());
        this.httpStatus = exceptionCodes.getHttpStatus();
        this.exceptionCode = exceptionCodes.getErrorCode();
        log.error("Exception : {} raised at : {}" , exceptionCodes.getErrorCode() , OffsetDateTime.now());
    }
    public BookingExceptions(ExceptionCodes exceptionCodes , Throwable throwable){
        super(exceptionCodes.getErrorMessage() , throwable);
        this.httpStatus = exceptionCodes.getHttpStatus();
        this.exceptionCode = exceptionCodes.getErrorCode();
        log.error(String.format(
                "Exception: %s raised at : %s",
                exceptionCodes.getErrorCode() , OffsetDateTime.now()
        ) , throwable);
    }
    public BookingExceptions(ExceptionCodes exceptionCodes , Object... values){
        super(String.format(exceptionCodes.getErrorMessage() , values));
        httpStatus = exceptionCodes.getHttpStatus();
        exceptionCode = exceptionCodes.getErrorCode();
        log.error("Exception : {} raised at : {}" , exceptionCodes.getErrorCode() , OffsetDateTime.now());
    }
}
