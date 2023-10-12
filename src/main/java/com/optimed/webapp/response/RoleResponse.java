package com.optimed.webapp.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;

@Data
public class RoleResponse {
    private long id;
    private String name;
    @JsonProperty("privileges")
    private Collection<PrivilegeResponse> privileges;
}
