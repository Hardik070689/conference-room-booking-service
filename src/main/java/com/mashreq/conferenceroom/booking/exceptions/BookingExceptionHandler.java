package com.mashreq.conferenceroom.booking.exceptions;

import com.mashreq.conferenceroom.booking.exceptions.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class BookingExceptionHandler {

    @ExceptionHandler({BookingExceptions.class})
    public ResponseEntity<ExceptionDto> handleRuntimeException(BookingExceptions exception) {
        return ResponseEntity
                .status(exception.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ExceptionDto(exception.getExceptionCode() , exception.getMessage()));
    }
}
