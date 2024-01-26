package com.mashreq.conferenceroom.booking.dto;

import java.util.List;

public record RoomConfigureDto(String roomName ,
                               Integer capacity,
                               boolean bookingEnabled ,
                               Integer minimumBookTimeRequired ,
                               Integer bookingFactor ,
                               List<MaintenanceTimeDto> maintenanceTimes){

}
