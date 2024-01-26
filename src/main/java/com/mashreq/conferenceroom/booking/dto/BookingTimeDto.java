package com.mashreq.conferenceroom.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BookingTimeDto(@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss" , shape = JsonFormat.Shape.STRING)
                             @NotNull
                             LocalDateTime startTime ,
                             @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss" , shape = JsonFormat.Shape.STRING)
                             @NotNull
                             LocalDateTime endTime){
}