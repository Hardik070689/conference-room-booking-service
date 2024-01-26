package com.mashreq.conferenceroom.booking;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test Cases to request params validation ")
@ActiveProfiles("test")
@ExtendWith({ SpringExtension.class})
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = {ConferenceRoomBookingApplication.class})
public class RequestParamsValidationTest {


    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    @Order(1)
    void test_whenInvalidTime_thenThrowError(){
        byte[] allBytes =  Files.readAllBytes(Path.of("src/test/resources/request/invalidStartTImeReq.json"));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/booking/availability")
                .content(new String(allBytes))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        assertTrue(mvcResult.getResponse().getStatus() != 200);

    }

}
