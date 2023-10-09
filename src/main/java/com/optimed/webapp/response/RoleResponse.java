package com.optimed.webapp.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Collection;

@Data
public class RoleResponse {
    private long id;
    private String name;
    @JsonIgnore
    private Collection<PrivilegeResponse> privileges;
}
