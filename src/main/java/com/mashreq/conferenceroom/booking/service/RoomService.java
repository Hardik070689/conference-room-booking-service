package com.mashreq.conferenceroom.booking.service;

import com.mashreq.conferenceroom.booking.dto.RoomConfigureDto;

public interface RoomService {

    public String configureRoom(RoomConfigureDto configureDto);
    public String update(RoomConfigureDto configureDto);

}
