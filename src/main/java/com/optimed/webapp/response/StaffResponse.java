package com.optimed.webapp.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;

@Data
public class StaffResponse implements Comparable<StaffResponse> {
    private long id;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String street;
    private String suburb;
    private String state;
    private String postcode;
    private String phone;
    private String email;
    private String password;
    private String providerNumber;
    private String prescriberNumber;
    private RoleResponse role;
    @Override
    public int compareTo(StaffResponse otherStaff) {
        return this.firstName.compareTo(otherStaff.getFirstName());
    }
}
