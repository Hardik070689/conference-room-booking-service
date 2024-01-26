package com.mashreq.conferenceroom.booking.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;


@Getter @Setter
@NoArgsConstructor
@Table(name="rooms_booking_history")
@Entity
public class RoomBookingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
    @JoinColumn(name="fk_room_id", nullable=false)
    private Room room;
    @Column(name="start_time")
    private LocalDateTime startDateTime;
    @Column(name="end_time")
    private LocalDateTime endDateTime;

    @CreationTimestamp
    @Column(name="created_on")
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Column(name="updated_on")
    private LocalDateTime updatedOn;

    public RoomBookingHistory(LocalDateTime startDateTime , LocalDateTime endDateTime , Room room){
        this.startDateTime = startDateTime ;
        this.endDateTime = endDateTime;
        this.room = room;
    }
}
