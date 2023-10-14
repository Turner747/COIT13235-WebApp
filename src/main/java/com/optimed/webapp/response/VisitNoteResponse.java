package com.optimed.webapp.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Collection;
import java.util.Date;

@Data
public class VisitNoteResponse {
    
    private Long id;
    
    @JsonProperty("doctor")
    private StaffResponse doctor;
    
    @JsonProperty("patient")
    private PatientResponse patient;
        
    private String content;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;
}
