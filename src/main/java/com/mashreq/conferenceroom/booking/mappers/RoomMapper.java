package com.mashreq.conferenceroom.booking.mappers;


import com.mashreq.conferenceroom.booking.dto.MaintenanceTimeDto;
import com.mashreq.conferenceroom.booking.dto.RoomConfigureDto;
import com.mashreq.conferenceroom.booking.model.Room;
import com.mashreq.conferenceroom.booking.model.RoomMaintenanceSchedule;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring" ,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS ,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {


    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "minimumBookingTime" , source = "minimumBookTimeRequired")
    @Mapping(target = "bookingTimeFactor" , source = "bookingFactor")
    @Mapping(target = "roomCapacity" , source = "capacity")
    @Mapping(target = "roomMaintenanceSchedules"  , expression = "java(mapMaintenanceSchedules(roomConfigureDto.maintenanceTimes()))")
    Room mapRoomConfiguration(RoomConfigureDto roomConfigureDto);
    default List<RoomMaintenanceSchedule> mapMaintenanceSchedules(List<MaintenanceTimeDto> maintenanceTimeDtos){
        if(CollectionUtils.isEmpty(maintenanceTimeDtos))
            return Collections.emptyList();
      return  maintenanceTimeDtos.stream().map(this::mapRoomSchedule).toList();
    }
    @Mapping(target = "id" ,ignore = true)
    RoomMaintenanceSchedule mapRoomSchedule(MaintenanceTimeDto maintenanceTimeDto);

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "roomCapacity" , source = "capacity")
    @Mapping(target = "bookingEnabled" , source = "bookingEnabled")
    @Mapping(target = "minimumBookingTime" , source = "minimumBookTimeRequired")
    void updateRoomDetails(@MappingTarget Room room , RoomConfigureDto roomConfigureDto);
}
