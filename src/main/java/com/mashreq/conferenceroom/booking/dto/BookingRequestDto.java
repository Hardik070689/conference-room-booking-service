package com.mashreq.conferenceroom.booking.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record BookingRequestDto(@JsonAlias({"person_capacity"})
                                @Pattern(regexp = "^(?:1[0-9]|20|[1-9])$" , message = "persons allowed for conference room booking should be in between (1) and (2)")
                                Integer personCapacity ,
                                @JsonAlias({"start_time"})
                                @NotNull
                                @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss" , shape = JsonFormat.Shape.STRING)
                                @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Invalid start time format. Please use 24-hour format.")
                                LocalDateTime startDime ,
                                @JsonAlias({"end_time"})
                                @NotNull
                                @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss" , shape = JsonFormat.Shape.STRING)
                                @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Invalid end time format. Please use 24-hour format.")
                                LocalDateTime endTime) {
}
