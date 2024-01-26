package com.mashreq.conferenceroom.booking.controller;

import com.mashreq.conferenceroom.booking.service.utils.ValidationUtils;
import com.mashreq.conferenceroom.booking.dto.BookingAvailabilityDto;
import com.mashreq.conferenceroom.booking.dto.BookingRequestDto;
import com.mashreq.conferenceroom.booking.dto.BookingResponseDto;
import com.mashreq.conferenceroom.booking.dto.BookingTimeDto;
import com.mashreq.conferenceroom.booking.service.BookingService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/v1/booking"  , produces = {MediaType.APPLICATION_JSON_VALUE})
public class BookingController {

    private final BookingService bookingService;
    private final ValidationUtils validationUtils;

    @PostMapping("/availability")
    public ResponseEntity<List<BookingAvailabilityDto>> checkAvailability(@RequestBody BookingTimeDto bookingTimeDto){
        return ResponseEntity.ok(bookingService.checkRoomAvailability(bookingTimeDto));
    }

    @PostMapping("/bookRoom")
    public ResponseEntity<BookingResponseDto> bookConferenceRoom(@RequestBody @NotNull BookingRequestDto bookingRequestDto) {
        validationUtils.validateBookingRequestDto(bookingRequestDto);
        return ResponseEntity.ok(bookingService.bookRoom(bookingRequestDto));
    }

}
