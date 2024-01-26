package com.mashreq.conferenceroom.booking.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {
    @JsonProperty("error_code") private String errorCode ;
    @JsonProperty("error_description") private String errorDescription ;
}
