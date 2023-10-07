package com.optimed.webapp.response;

import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
public class StaffResponse {
    private long id;
    private String firstName;
    private String lastName;
    private Date dob;
    private String street;
    private String suburb;
    private String state;
    private int postcode;
    private String phone;
    private String email;
    private String password;
    private String providerNumber;
    private String prescriberNumber;
    private Collection<RoleResponse> roles;
}
