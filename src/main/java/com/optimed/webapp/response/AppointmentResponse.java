package com.optimed.webapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AppointmentResponse {
    private long id;
    private PatientResponse patient;
    private StaffResponse doctor;
    private ShiftResponse shift;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date finishTime;
    private String statusEnum;
}