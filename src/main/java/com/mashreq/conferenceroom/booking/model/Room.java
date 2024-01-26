package com.mashreq.conferenceroom.booking.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "rooms")
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "room_name")
    private String roomName;
    @Column(name = "room_capacity")
    private Integer roomCapacity;
    @CreationTimestamp
    @Column(name="created_on")
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Column(name="updated_on")
    private LocalDateTime updatedOn;
    @OneToMany(fetch = FetchType.LAZY,mappedBy="room" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<RoomMaintenanceSchedule> roomMaintenanceSchedules;
    @OneToMany(fetch = FetchType.LAZY,mappedBy="room" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<RoomBookingHistory> roomBookingHistories;
    @Column(name="booking_time_factor")
    private Integer bookingTimeFactor;
    @Column(name="minimum_booking_time")
    private Integer minimumBookingTime;
    @Column(name="booking_enabled")
    private boolean bookingEnabled;
}
