package com.mashreq.conferenceroom.booking.controller;

import com.mashreq.conferenceroom.booking.dto.RoomConfigureDto;
import com.mashreq.conferenceroom.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/v1/room"  , produces = {MediaType.APPLICATION_JSON_VALUE})
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/configure")
    public ResponseEntity<String> configureRoom(@RequestBody RoomConfigureDto roomConfigureDto){
        return ResponseEntity.ok(roomService.configureRoom(roomConfigureDto));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateRoom(@RequestBody RoomConfigureDto roomConfigureDto){
        return ResponseEntity.ok(roomService.update(roomConfigureDto));
    }



}
