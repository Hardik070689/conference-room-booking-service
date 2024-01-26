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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final RoomRepository roomRepository;
    private final BookingServiceHelper bookingServiceHelper;

    /**
     * Checks the availability of rooms for booking within the specified time frame.
     *
     * @param bookingTimeDto The DTO containing the start and end times of the booking.
     * @return A list of {@code BookingAvailabilityDto} objects representing the availability of rooms.
     */
    @Override
    public List<BookingAvailabilityDto> checkRoomAvailability(BookingTimeDto bookingTimeDto) {

        // Log the request for checking room availability
        log.info("Request received to checking room availability between : {} and {}"  ,
                bookingTimeDto.startTime() , bookingTimeDto.endTime());
        // Retrieve all rooms from the repository
        List<Room> allRooms = roomRepository.findAll();
        // Initialize a list to store the availability responses
        List<BookingAvailabilityDto> responseAvailability = new ArrayList<>();
        // Get the requested booking time in minutes
        List<Integer> requestBookingInMinutes = bookingServiceHelper.getRequestedBookingInMinutes(bookingTimeDto.startTime() , bookingTimeDto.endTime());
        // Iterate through each room to check availability
        for(Room room : allRooms){

            Integer timeDiffInMinutes = bookingServiceHelper.getTimeDiffInMinutes(
                    bookingTimeDto.startTime() , bookingTimeDto.endTime()
            );
            if(!bookingServiceHelper.isOpenForBooking(room , timeDiffInMinutes)){
                // it means the room is not open for booking / or does not meet booking criteria
                continue;
            }

            // Get booked time in minutes for the current room
            List<Integer> bookedTimeInMinutes = bookingServiceHelper.collectDayRoomBookingAndMaintenanceMinutes(room);
            // If the room has no booked time, add it to the response as available
            if(CollectionUtils.isEmpty(bookedTimeInMinutes)){
                responseAvailability.add(new BookingAvailabilityDto(room.getRoomName() ,
                        LocalDate.now()  , room.getRoomCapacity() , null ));
                continue;
            }
            // Check for intersection between booked time and requested booking time
            Collection<Integer> intersectedMinutes =  CollectionUtils.intersection(bookedTimeInMinutes,requestBookingInMinutes);
            // If no intersection, add the room to the response as available
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
                bookingRequestDto.startTime() , bookingRequestDto.endTime());

        List<Room> rooms = roomRepository.getRoomsOrderByCapacityAsc();

        for(Room room: rooms){
            Integer timeDiffInMinutes = bookingServiceHelper.getTimeDiffInMinutes(
                    bookingRequestDto.startTime() , bookingRequestDto.endTime()
            );
            if(!bookingServiceHelper.isOpenForBooking(room , timeDiffInMinutes)){
                // it means the room is not open for booking / or does not meet booking criteria
                continue;
            }

            if(bookingRequestDto.personCapacity() > room.getRoomCapacity()){
                // persons requested for meeting room are more than available capacity of the room
                continue;
            }
            List<Integer> roomMaintenanceMinutesList = bookingServiceHelper.collectMaintenanceTimesBookingMinutes(room);
            List<Integer> requestBookingInMinutes = bookingServiceHelper.getRequestedBookingInMinutes(bookingRequestDto.startTime() ,
                    bookingRequestDto.endTime() );

            Collection<Integer> intersectedMinutes =  CollectionUtils.intersection(roomMaintenanceMinutesList , requestBookingInMinutes);
            if(CollectionUtils.isNotEmpty(intersectedMinutes)){
                throw new BookingExceptions(ExceptionCodes.BOOKING_COLLIDES_WITH_MAINTENANCE_TIMES);
            }

            List<Integer> roomBookingMinutes = bookingServiceHelper.collectDayRoomBookingMinutes(room);
            intersectedMinutes =  CollectionUtils.intersection(roomBookingMinutes , requestBookingInMinutes);

            // this means the booking is available
            if(CollectionUtils.isEmpty(intersectedMinutes)){
                return createNewBooking(room , bookingRequestDto.startTime() , bookingRequestDto.endTime());
            }
        }
        throw new BookingExceptions(ExceptionCodes.BOOKING_COLLIDES_WITH_EXISTING_BOOKING , bookingRequestDto.startTime() , bookingRequestDto.endTime());
    }


    /**
     * Logs the request to book a meeting room for a specified number of persons within a time frame.
     * Retrieves a list of rooms ordered by capacity in ascending order and iterates through each room to check availability.
     * If the requested number of persons exceeds the room's capacity, skips to the next room.
     * Checks for maintenance times that overlap with the requested booking time and throws an exception if found.
     * Checks for existing bookings that overlap with the requested booking time and throws an exception if found.
     * If no conflicts are found, creates a new booking for the room and returns the booking details.
     *
     * @return The details of the newly created booking.
     * @throws BookingExceptions Thrown if the booking collides with maintenance times or existing bookings.
     */

@Transactional
BookingResponseDto createNewBooking(Room room , LocalDateTime startDateTime , LocalDateTime endDateTime){


        RoomBookingHistory roomBookingHistory = new RoomBookingHistory(startDateTime , endDateTime ,
                room);

        if(CollectionUtils.isEmpty(room.getRoomBookingHistories()))
            room.getRoomBookingHistories().add(roomBookingHistory);
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
