package com.mashreq.conferenceroom.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record BookingAvailabilityDto (String roomName ,
                                      @JsonFormat(pattern = "dd/MM/yyyy" , shape = JsonFormat.Shape.STRING)
                                      LocalDate availableDate ,
                                      Integer personCount,
                                      @Schema(hidden = true)
                                      List<BookingTimeDto> bookingTimes){

}

