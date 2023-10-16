package com.optimed.webapp.feignclient;

import com.optimed.webapp.response.PasswordResponse;
import com.optimed.webapp.response.RoleResponse;
import com.optimed.webapp.response.ShiftResponse;
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
    @GetMapping("restapi/staffs/doctors")
    public ResponseEntity<Collection<StaffResponse>> getAllDoctors();
    @GetMapping("restapi/staffs/id/{id}")
    public ResponseEntity<StaffResponse> getStaffByID(@PathVariable("id") long id);
    @GetMapping("restapi/staffs/email/{email}")
    public ResponseEntity<StaffResponse> getStaffByEmail(@PathVariable("email") String email);
    @PostMapping(value = "restapi/staffs", produces = "application/json", consumes = "application/json")
    public ResponseEntity<StaffResponse> saveStaff(@RequestBody StaffResponse staff);
    @GetMapping("restapi/shifts")
    public ResponseEntity<Collection<ShiftResponse>> getAllShifts();
    @GetMapping("restapi/shifts/id/{id}")
    public ResponseEntity<ShiftResponse> getShiftById(@PathVariable("id") long id);
    @PostMapping(value = "restapi/shifts", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ShiftResponse> saveShift(@RequestBody ShiftResponse shift);
    @GetMapping(value = "restapi/shifts/delete/{id}")
    public ResponseEntity<ShiftResponse> deleteShiftById(@PathVariable("id") long id);
    @GetMapping(value = "restapi/shifts/staff/{id}")
    public ResponseEntity<Collection<ShiftResponse>> getShiftByStaffId(@PathVariable("id") long id);
    @GetMapping(value = "restapi/staffs/delete/{id}")
    public ResponseEntity<StaffResponse> deleteStaffById(@PathVariable("id") long id);
    @GetMapping(value = "restapi/staffs/password/{id}")
    public ResponseEntity<PasswordResponse> getPasswordById(@PathVariable("id") long id);
}
