package com.optimed.webapp.response;

import lombok.Data;

import java.util.Collection;

@Data
public class RoleResponse {
    private long id;
    private String name;
    private Collection<PrivilegeResponse> privileges;
}
