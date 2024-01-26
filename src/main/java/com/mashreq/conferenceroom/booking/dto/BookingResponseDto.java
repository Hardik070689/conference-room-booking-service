package com.mashreq.conferenceroom.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record BookingResponseDto(String roomName ,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
                                 LocalDateTime startDateTime ,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
                                 LocalDateTime endDateTime ,
                                 boolean bookingSuccess ,
                                 List<BookingTimeDto> alternateAvailabilities) {
}
