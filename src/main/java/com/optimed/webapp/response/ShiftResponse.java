package com.optimed.webapp.response;

import lombok.Data;

import java.util.Date;
@Data
public class ShiftResponse {
    private long id;
    private StaffResponse staff;
    private Date startTime;
    private Date finishTime;
}