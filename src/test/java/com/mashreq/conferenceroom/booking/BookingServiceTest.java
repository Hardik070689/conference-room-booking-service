package com.mashreq.conferenceroom.booking;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashreq.conferenceroom.booking.dto.BookingAvailabilityDto;
import com.mashreq.conferenceroom.booking.dto.BookingRequestDto;
import com.mashreq.conferenceroom.booking.dto.BookingResponseDto;
import com.mashreq.conferenceroom.booking.dto.BookingTimeDto;
import com.mashreq.conferenceroom.booking.exceptions.dto.ExceptionDto;
import com.mashreq.conferenceroom.booking.service.BookingService;
import com.mashreq.conferenceroom.booking.utils.BookingTestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Test Cases to test Room Booking Availability times ")
@ActiveProfiles("test")
@ExtendWith({ SpringExtension.class})
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = {ConferenceRoomBookingApplication.class})
public class BookingServiceTest {


    @Autowired
    BookingService bookingService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    public void test_whenBookDuringMaintainTime_Then_NoAvailableBookings(){
        LocalTime startTime = LocalTime.NOON.minusMinutes(200);
        LocalTime endTime = LocalTime.NOON.minusMinutes(150);
        BookingTimeDto bookingTimeDto = new BookingTimeDto(
                BookingTestUtils.getTodayLocalDateTime(startTime) ,
                BookingTestUtils.getTodayLocalDateTime(endTime));
        List<BookingAvailabilityDto> responseList = bookingService.checkRoomAvailability(bookingTimeDto);
        assertEquals(0  , responseList.size());
    }

    @Test
    @Order(2)
    public void test_whenBookOutOfMaintainTime_Then_AvailableBookings(){
        LocalTime startTime = LocalTime.NOON.minusMinutes(60);
        LocalTime endTime = LocalTime.NOON.minusMinutes(75);
        BookingTimeDto bookingTimeDto = new BookingTimeDto(
                BookingTestUtils.getTodayLocalDateTime(startTime ),
                BookingTestUtils.getTodayLocalDateTime(endTime)
        );
        List<BookingAvailabilityDto> responseList = bookingService.checkRoomAvailability(bookingTimeDto);
        assertEquals(4  , responseList.size());
    }



    @Test
    @Order(3)
    public void test_whenBookingDuringValidTime_ThenBookingSuccessFull(){
        LocalTime startTime = LocalTime.NOON.minusMinutes(10);
        LocalTime endTime = LocalTime.NOON.plusMinutes(5);
        BookingRequestDto bookingRequestDto = new BookingRequestDto(3 ,
                BookingTestUtils.getTodayLocalDateTime(startTime) ,
                BookingTestUtils.getTodayLocalDateTime(endTime));
        BookingResponseDto bookingResponseDto = bookingService.bookRoom(bookingRequestDto);
        assertTrue(bookingResponseDto.bookingSuccess());
        assertEquals( "Amaze" , bookingResponseDto.roomName());
    }


    @Test
    @Order(4)
    public void test_whenBookingDuringValidTime_ThenBookingSuccessFull_WithBigRoom(){
        LocalTime startTime = LocalTime.NOON.minusMinutes(10);
        LocalTime endTime = LocalTime.NOON.plusMinutes(5);
        BookingRequestDto bookingRequestDto = new BookingRequestDto(3 ,
                BookingTestUtils.getTodayLocalDateTime(startTime) ,
                BookingTestUtils.getTodayLocalDateTime(endTime));
        BookingResponseDto bookingResponseDto = bookingService.bookRoom(bookingRequestDto);
        assertTrue(bookingResponseDto.bookingSuccess());
        assertEquals( "Beauty" , bookingResponseDto.roomName());
        assertThrows(Exception.class , () -> bookingService.bookRoom(new BookingRequestDto(25  ,
                BookingTestUtils.getTodayLocalDateTime(startTime.plusMinutes(20) ) ,
                BookingTestUtils.getTodayLocalDateTime( endTime.plusMinutes( 35)))));
    }

    @Test
    @SneakyThrows
    @Order(5)
    public void test_whenBookingInPastTime_ThenThrowException(){
        BookingRequestDto bookingRequestDto = new BookingRequestDto(3 ,
                BookingTestUtils.getTodayLocalDateTime(LocalTime.now().minusMinutes(20) ),
                BookingTestUtils.getTodayLocalDateTime(LocalTime.now().minusMinutes(5)));
       MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/booking/bookRoom")
                .content( objectMapper.writeValueAsString(bookingRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
      assertEquals(400 ,  mvcResult.getResponse().getStatus());
     ExceptionDto exceptionDto = objectMapper.readValue(
             mvcResult.getResponse().getContentAsString() ,
             ExceptionDto.class);
      assertEquals(  "BOOKING_EXCEPTION_003" , exceptionDto.getErrorCode());
    }


    @Test
    @Order(6)
    @SneakyThrows
    public void test_whenBookingfor10Minutes_ThenThrowException(){
        BookingRequestDto bookingRequestDto = new BookingRequestDto(3 ,
                BookingTestUtils.getTodayLocalDateTime(LocalTime.now().plusMinutes(20)) ,
                BookingTestUtils.getTodayLocalDateTime(LocalTime.now().plusMinutes(30)));
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/booking/bookRoom")
                .content( objectMapper.writeValueAsString(bookingRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(400 ,  mvcResult.getResponse().getStatus());
        ExceptionDto exceptionDto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString() ,
                ExceptionDto.class);
        assertEquals(  "BOOKING_EXCEPTION_004" , exceptionDto.getErrorCode());
    }

    @Test
    @SneakyThrows
    @Order(7)
    public void test_whenMultiDayBooking_ThenThrowException(){

        BookingRequestDto bookingRequestDto = new BookingRequestDto(3 ,
                BookingTestUtils.getLocalDateTime(LocalDateTime.now().plusDays(1), LocalTime.now().plusMinutes(20)) ,
                BookingTestUtils.getLocalDateTime(LocalDateTime.now().plusDays(1), LocalTime.now().plusMinutes(30)));
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/booking/bookRoom")
                .content( objectMapper.writeValueAsString(bookingRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(400 ,  mvcResult.getResponse().getStatus());
        ExceptionDto exceptionDto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString() ,
                ExceptionDto.class);
        assertEquals(  "BOOKING_EXCEPTION_007" , exceptionDto.getErrorCode());

    }

    @Test
    @SneakyThrows
    @Order(8)
    public void test_whenEndStartTimeEqual_ThenThrowException(){

        BookingRequestDto bookingRequestDto = new BookingRequestDto(3 ,
                BookingTestUtils.getTodayLocalDateTime( LocalTime.now().plusMinutes(20)) ,
                BookingTestUtils.getTodayLocalDateTime(  LocalTime.now().plusMinutes(20)));
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/booking/bookRoom")
                .content( objectMapper.writeValueAsString(bookingRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(400 ,  mvcResult.getResponse().getStatus());
        ExceptionDto exceptionDto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString() ,
                ExceptionDto.class);
        assertEquals(  "BOOKING_EXCEPTION_006" , exceptionDto.getErrorCode());

    }

    @Test
    @Order(99)
    public void dummyTest(){

        LocalTime localTime = LocalTime.NOON.minusMinutes(150);
        Integer hour = localTime.getHour();
        Integer minute = localTime.getMinute();
        assertEquals( (hour * 60  + minute) , 570);

        localTime = LocalTime.MIN.plusMinutes(19);
        hour = localTime.getHour();
        minute = localTime.getMinute();
        assertEquals(19 , (hour * 60  + minute));

    }

}
