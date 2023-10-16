package com.optimed.webapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Collection;
import java.util.Date;

@Data
public class ConditionResponse {
    
    private Long id;
    
    @JsonProperty("doctor")
    private StaffResponse doctor;
    
    @JsonProperty("patient")
    private PatientResponse patient;
    
    private String diagnosis;
    private String notes;
  
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date diagnosisDate;
}
