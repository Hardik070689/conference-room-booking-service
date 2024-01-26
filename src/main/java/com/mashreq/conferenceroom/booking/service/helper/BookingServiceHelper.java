package com.mashreq.conferenceroom.booking.service.helper;


import com.mashreq.conferenceroom.booking.model.Room;
import com.mashreq.conferenceroom.booking.model.RoomBookingHistory;
import com.mashreq.conferenceroom.booking.model.RoomMaintenanceSchedule;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceHelper {


    public boolean isOpenForBooking(Room room , int bookingMinutes){
        if(!room.isBookingEnabled())
            return false ;
        if(bookingMinutes< room.getMinimumBookingTime())
            return false ;
        if((bookingMinutes % room.getBookingTimeFactor()) >0)
            return false;
        return true ;
    }

    public Integer getTimeDiffInMinutes(LocalDateTime startDateTime ,
                                        LocalDateTime endDateTime){
        return (int)Duration.between(startDateTime , endDateTime).toMinutes();
    }

    public List<Integer> collectDayRoomBookingMinutes(Room room){
        List<Integer> roomBookingMinutes = new ArrayList<>();
        for(RoomBookingHistory roomBookingHistory : room.getRoomBookingHistories()){
            Integer startTimeInMinutes  = calculateTimeInMinutes(roomBookingHistory.getStartDateTime());
            Integer endTimeInMinutes = calculateTimeInMinutes(roomBookingHistory.getEndDateTime());
            roomBookingMinutes.addAll(getRangeOfNumbers(startTimeInMinutes , endTimeInMinutes));
        }
        return roomBookingMinutes;
    }
    public List<Integer> collectDayRoomBookingAndMaintenanceMinutes(Room room) {
        List<Integer> bookingAndMaintenanceTimes = new ArrayList<>();
        List<Integer> maintenanceTimeMinutes  = collectMaintenanceTimesBookingMinutes(room);
        if(CollectionUtils.isNotEmpty(maintenanceTimeMinutes)){
            bookingAndMaintenanceTimes.addAll(maintenanceTimeMinutes);
        }

        for(RoomBookingHistory roomBookingHistory : room.getRoomBookingHistories()){
            Integer startTimeInMinutes  = calculateTimeInMinutes(roomBookingHistory.getStartDateTime());
            Integer endTimeInMinutes = calculateTimeInMinutes(roomBookingHistory.getEndDateTime());
            bookingAndMaintenanceTimes.addAll(getRangeOfNumbers(startTimeInMinutes , endTimeInMinutes));
        }
        return bookingAndMaintenanceTimes;
    }

    public List<Integer> collectMaintenanceTimesBookingMinutes(Room room){
        List<Integer> bookingAndMaintenanceTimes = new ArrayList<>();
        for(RoomMaintenanceSchedule maintenanceSchedule: room.getRoomMaintenanceSchedules()){
            Integer startTimeInMinutes = calculateTimeInMinute( maintenanceSchedule.getStartTime() );
            Integer endTimeInMinutes  = calculateTimeInMinute(maintenanceSchedule.getEndTime() );
            bookingAndMaintenanceTimes.addAll(getRangeOfNumbers(startTimeInMinutes , endTimeInMinutes));
        }
        return  bookingAndMaintenanceTimes;
    }

    private Integer calculateTimeInMinute(LocalTime time){
        int hour = time.getHour();
        int minute = time.getMinute();
        return (hour * 60  + minute);
    }

    private Integer calculateTimeInMinutes(LocalDateTime localDateTime){
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        return (hour * 60  + minute);
    }

    List<Integer> getRangeOfNumbers(Integer startNumber , Integer endNumber){
        List<Integer> storedTimeMinutes = new ArrayList<>();
        for(int count=startNumber ;count<endNumber ;count++){
            storedTimeMinutes.add(count);
        }
        return storedTimeMinutes;
    }

    public List<Integer> getRequestedBookingInMinutes(LocalDateTime startTime , LocalDateTime endTime){
        Integer startTimeMinute = calculateTimeInMinutes(startTime);
        Integer endTimeMinute = calculateTimeInMinutes(endTime);
        return getRangeOfNumbers(startTimeMinute , endTimeMinute);
    }

}
