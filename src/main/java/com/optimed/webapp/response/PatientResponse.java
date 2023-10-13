package com.optimed.webapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PatientResponse implements Comparable<PatientResponse>{
    
    private Long id;
    private String firstName;
    private String surname;
    private String phoneNbr;
    private String emailAddress;
    private String street;
    private String suburb;
    private String state;
    private String postcode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate;

    @Override
    public int compareTo(PatientResponse otherPatient) {
        return this.firstName.compareTo(otherPatient.getFirstName());
    }
}