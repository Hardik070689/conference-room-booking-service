package com.mashreq.conferenceroom.booking.service.impl;

import com.mashreq.conferenceroom.booking.dto.BookingAvailabilityDto;
import com.mashreq.conferenceroom.booking.dto.BookingRequestDto;
import com.mashreq.conferenceroom.booking.dto.BookingResponseDto;
import com.mashreq.conferenceroom.booking.dto.BookingTimeDto;
import com.mashreq.conferenceroom.booking.exceptions.BookingExceptions;
import com.mashreq.conferenceroom.booking.exceptions.enums.ExceptionCodes;
import com.mashreq.conferenceroom.booking.model.Room;
import com.mashreq.conferenceroom.booking.model.RoomBookingHistory;
import com.mashreq.conferenceroom.booking.repository.RoomRepository;
import com.mashreq.conferenceroom.booking.service.BookingService;
import com.mashreq.conferenceroom.booking.service.helper.BookingServiceHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final RoomRepository roomRepository;
    private final BookingServiceHelper bookingServiceHelper;

    @Override
    public List<BookingAvailabilityDto> checkRoomAvailability(BookingTimeDto bookingTimeDto) {

        log.info("Request received to checking room availability between : {} and {}"  ,
                bookingTimeDto.startTime() , bookingTimeDto.endTime());
        List<Room> allRooms = roomRepository.findAll();
        List<BookingAvailabilityDto> responseAvailability = new ArrayList<>();
        List<Integer> requestBookingInMinutes = bookingServiceHelper.getRequestedBookingInMinutes(bookingTimeDto.startTime() , bookingTimeDto.endTime());

        for(Room room : allRooms){
            List<Integer> bookedTimeInMinutes = bookingServiceHelper.collectDayRoomBookingAndMaintenanceMinutes(room);
            if(CollectionUtils.isEmpty(bookedTimeInMinutes)){
                responseAvailability.add(new BookingAvailabilityDto(room.getRoomName() ,
                        LocalDate.now()  , room.getRoomCapacity() , null ));
                continue;
            }
            Collection<Integer> intersectedMinutes =  CollectionUtils.intersection(bookedTimeInMinutes,requestBookingInMinutes);
            if(intersectedMinutes.isEmpty()){
                responseAvailability.add(new BookingAvailabilityDto(room.getRoomName() ,
                        LocalDate.now()  , room.getRoomCapacity() , null ));
            }
        }
        return responseAvailability;
    }

    @Override
    public BookingResponseDto bookRoom(BookingRequestDto bookingRequestDto) {

        log.info("Request received to book the meeting room for ({}) persons between ({}) and ({})" ,
                bookingRequestDto.personCapacity()  ,
                bookingRequestDto.startDime() , bookingRequestDto.endTime());

        List<Room> rooms = roomRepository.getRoomsOrderByCapacityAsc();

        for(Room room: rooms){
            if(bookingRequestDto.personCapacity() > room.getRoomCapacity()){
                // persons requested for meeting room are more than available capacity of the room
                continue;
            }
            List<Integer> roomMaintenanceMinutesList = bookingServiceHelper.collectMaintenanceTimesBookingMinutes(room);
            List<Integer> requestBookingInMinutes = bookingServiceHelper.getRequestedBookingInMinutes(bookingRequestDto.startDime() ,
                    bookingRequestDto.endTime() );

            Collection<Integer> intersectedMinutes =  CollectionUtils.intersection(roomMaintenanceMinutesList , requestBookingInMinutes);
            if(CollectionUtils.isNotEmpty(intersectedMinutes)){
                throw new BookingExceptions(ExceptionCodes.BOOKING_COLLIDES_WITH_MAINTENANCE_TIMES);
            }

            List<Integer> roomBookingMinutes = bookingServiceHelper.collectDayRoomBookingMinutes(room);
            intersectedMinutes =  CollectionUtils.intersection(roomBookingMinutes , requestBookingInMinutes);

            // this means the booking is available
            if(CollectionUtils.isEmpty(intersectedMinutes)){
                return createNewBooking(room , bookingRequestDto.startDime() , bookingRequestDto.endTime());
            }
        }
        throw new BookingExceptions(ExceptionCodes.BOOKING_COLLIDES_WITH_EXISTING_BOOKING);
    }


    BookingResponseDto createNewBooking(Room room ,LocalDateTime startDateTime , LocalDateTime endDateTime){


        RoomBookingHistory roomBookingHistory = new RoomBookingHistory(startDateTime , endDateTime ,
                room);

        if(CollectionUtils.isEmpty(room.getRoomBookingHistories()))
            room.setRoomBookingHistories(List.of(roomBookingHistory));
        else
            room.getRoomBookingHistories().add(roomBookingHistory);
        roomRepository.save(room);
        log.info("Blocking/Saving the room :{} between {} and {} time" ,
                room.getRoomName() , startDateTime , endDateTime);
        return new BookingResponseDto(room.getRoomName() ,
                startDateTime,
                endDateTime,
                true ,
                Collections.emptyList()
        );
    }
}
