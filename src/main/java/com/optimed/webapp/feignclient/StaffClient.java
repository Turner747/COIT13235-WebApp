package com.optimed.webapp.feignclient;

import com.optimed.webapp.response.RoleResponse;
import com.optimed.webapp.response.StaffResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@FeignClient(name="staff-service")
public interface StaffClient {
    @GetMapping("restapi/roles")
    public ResponseEntity<Collection<RoleResponse>> getAllRoles();
    @GetMapping("restapi/roles/id/{id}")
    public ResponseEntity<RoleResponse> getRoleByID(@PathVariable("id") long id);
    @GetMapping("restapi/roles/name/{name}")
    public ResponseEntity<RoleResponse> getRoleByName(@PathVariable("name") String name);
    @GetMapping("restapi/staffs")
    public ResponseEntity<Collection<StaffResponse>> getAllStaffs();
    @GetMapping("restapi/staffs/id/{id}")
    public ResponseEntity<StaffResponse> getStaffByID(@PathVariable("id") long id);
    @GetMapping("restapi/staffs/email/{email}")
    public ResponseEntity<StaffResponse> getStaffByEmail(@PathVariable("email") String email);
    @PostMapping(value = "restapi/staffs", produces = "application/json", consumes = "application/json")
    public ResponseEntity<StaffResponse> saveStaff(@RequestBody StaffResponse staff);//, @RequestBody Collection<RoleResponse> roles);
}
