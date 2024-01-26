package com.mashreq.conferenceroom.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public record MaintenanceTimeDto (
        @JsonFormat(pattern = "HH:mm" , shape = JsonFormat.Shape.STRING)LocalTime startTime ,
        @JsonFormat(pattern = "HH:mm" , shape = JsonFormat.Shape.STRING)LocalTime endTime){
}
