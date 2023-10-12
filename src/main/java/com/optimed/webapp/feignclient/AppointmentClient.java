package com.optimed.webapp.feignclient;

import com.optimed.webapp.response.AppointmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@FeignClient("appointment-service")
public interface AppointmentClient {
    @GetMapping("restapi/appointments")
    public ResponseEntity<Collection<AppointmentResponse>> getAllAppointments();
    @GetMapping("restapi/appointments/id/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable("id") long id);
    @PostMapping(value = "restapi/appointments", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AppointmentResponse> saveAppointment(@RequestBody AppointmentResponse appointmentResponse);
}
