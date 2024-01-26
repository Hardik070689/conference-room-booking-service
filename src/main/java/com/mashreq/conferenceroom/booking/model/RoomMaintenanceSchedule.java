package com.mashreq.conferenceroom.booking.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Setter
@Table(name="rooms_maintenance_schedules")
@Entity
public class RoomMaintenanceSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
    @JoinColumn(name="fk_room_id", nullable=false)
    private Room room;
    @Column(name="time_start")
    private LocalTime startTime;
    @Column(name="time_end")
    private LocalTime endTime;
    @CreationTimestamp
    @Column(name="created_on")
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Column(name="updated_on")
    private LocalDateTime updatedOn;
}
