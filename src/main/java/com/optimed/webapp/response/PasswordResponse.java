package com.optimed.webapp.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PasswordResponse {
    private long id;
    private String password;
}