package com.optimed.webapp.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Collection;
import java.util.Date;

@Data
public class ConditionResponse {
    
    private Long id;
    private StaffResponse doctor;
    private PatientResponse patient;
    private String diagnosis;
    private String notes;
  
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date diagnosisDate;

}
