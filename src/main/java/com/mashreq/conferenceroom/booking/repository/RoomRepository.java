package com.mashreq.conferenceroom.booking.repository;

import com.mashreq.conferenceroom.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room , Long> {


    @Query("select room from Room room order by room.roomCapacity asc")
    List<Room> getRoomsOrderByCapacityAsc();

}
