package com.optimed.webapp.response;

import lombok.Data;

@Data
public class PatientResponse {
    private long id;
    private String firstName;
    private String lastName;
}