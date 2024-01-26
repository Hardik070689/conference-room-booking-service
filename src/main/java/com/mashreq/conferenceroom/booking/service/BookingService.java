package com.mashreq.conferenceroom.booking.service;

import com.mashreq.conferenceroom.booking.dto.BookingAvailabilityDto;
import com.mashreq.conferenceroom.booking.dto.BookingRequestDto;
import com.mashreq.conferenceroom.booking.dto.BookingResponseDto;
import com.mashreq.conferenceroom.booking.dto.BookingTimeDto;

import java.util.List;

public interface BookingService {
        List<BookingAvailabilityDto> checkRoomAvailability(BookingTimeDto bookingTimeDto);

        BookingResponseDto bookRoom(BookingRequestDto bookingRequestDto);
}
