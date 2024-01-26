package com.mashreq.conferenceroom.booking.service.impl;

import com.mashreq.conferenceroom.booking.dto.RoomConfigureDto;
import com.mashreq.conferenceroom.booking.exceptions.BookingExceptions;
import com.mashreq.conferenceroom.booking.mappers.RoomMapper;
import com.mashreq.conferenceroom.booking.model.Room;
import com.mashreq.conferenceroom.booking.repository.RoomRepository;
import com.mashreq.conferenceroom.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.mashreq.conferenceroom.booking.exceptions.enums.ExceptionCodes.ROOM_BOOKING_NOT_EXISTS;
import static com.mashreq.conferenceroom.booking.exceptions.enums.ExceptionCodes.ROOM_CONFIGURE_DUPLICATE_EXISTS;


@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;
    @Override
    public String configureRoom(RoomConfigureDto configureDto) {
        log.info("Request received to configure the room by name: {}" , configureDto.roomName() );
        Room room = roomMapper.mapRoomConfiguration(configureDto);
        if(Objects.nonNull(roomRepository.findRoomByName(configureDto.roomName()))){
            throw new BookingExceptions(ROOM_CONFIGURE_DUPLICATE_EXISTS ,
                    configureDto.roomName());
        }
        if(Objects.nonNull(room))
            roomRepository.save(room);
        return String.format("Room (%s) with capacity (%s) configured" ,
                configureDto.roomName() , configureDto.capacity());
    }

    @Override
    public String update(RoomConfigureDto configureDto) {
        log.info("Request recieved to update the configuration parameters for room Name : {}" ,
                configureDto.roomName());

        Room roomToUpdate = roomRepository.findRoomByName(configureDto.roomName());
        if(Objects.isNull(roomToUpdate)){
            throw new BookingExceptions(ROOM_BOOKING_NOT_EXISTS ,
                    configureDto.roomName());
        }
        roomMapper.updateRoomDetails(roomToUpdate , configureDto);
        roomRepository.save(roomToUpdate);
        return  String.format("Room Configurations for room : (%s) updated" ,
                configureDto.roomName() );
    }
}
