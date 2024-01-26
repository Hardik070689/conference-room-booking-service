package com.mashreq.conferenceroom.booking;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = {"com.mashreq.*"} )
@EnableWebMvc
public class ConferenceRoomBookingApplication {
    @Generated
    public static void main(String[] args) {
        SpringApplication.run(ConferenceRoomBookingApplication.class, args);
    }
}
