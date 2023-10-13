package com.optimed.webapp.controller;

import com.optimed.webapp.feignclient.AppointmentClient;
import com.optimed.webapp.feignclient.PatientClient;
import com.optimed.webapp.feignclient.StaffClient;
import com.optimed.webapp.mappper.ObjectMapper;
import com.optimed.webapp.response.AppointmentResponse;
import com.optimed.webapp.response.PatientResponse;
import com.optimed.webapp.response.ShiftResponse;
import com.optimed.webapp.response.StaffResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    AppointmentClient appointmentClient;
    @Autowired
    StaffClient staffClient;
    @Autowired
    PatientClient patientClient;
    @PostMapping(value = "/save", consumes = "*/*")
    public String saveAppointment(@ModelAttribute("appointmentDetail") AppointmentResponse appointment,
                            Model model) {
        try {
//            System.out.println(appointment);
            appointmentClient.saveAppointment(appointment);
        } catch(Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "errors/appointment-error"; // something needs fixing here
        }
        return "redirect:/appointments";
    }
    @GetMapping("/add/{id}")
    public String addAppointment(@PathVariable("id") long id, Model model) {
        ShiftResponse shift = ObjectMapper.map(staffClient.getShiftById(id).getBody(), ShiftResponse.class);
        List<PatientResponse> patientResponseList = ObjectMapper.mapAll(patientClient.getAllPatients().getBody(), PatientResponse.class);
        Collections.sort(patientResponseList);
        model.addAttribute("appointmentDetail", new AppointmentResponse());
        model.addAttribute("shiftDetail", shift);
        model.addAttribute("allPatients", patientResponseList);
        return "appointments/add";
    }
    @GetMapping("/select-doctor")
    public String selectDoctor(Model model) {
        List<StaffResponse> doctors = ObjectMapper.mapAll(staffClient.getAllDoctors().getBody(), StaffResponse.class);
        Collections.sort(doctors);
        List<ShiftResponse> shifts = new ArrayList<ShiftResponse>();
        for(StaffResponse doctor : doctors) {
            shifts.addAll( ObjectMapper.mapAll(staffClient.getShiftByStaffId(doctor.getId()).getBody(), ShiftResponse.class) );
        }
        model.addAttribute("allShifts", shifts);
        return "appointments/select-doctor";
    }
    @GetMapping("/update/{id}")
    public String updateAppointment(@PathVariable("id") long id, Model model) {
        AppointmentResponse appointment = ObjectMapper.map(appointmentClient.getAppointmentById(id).getBody(), AppointmentResponse.class);
        ShiftResponse shift = ObjectMapper.map(staffClient.getShiftById(appointment.getDoctor().getId()).getBody(), ShiftResponse.class);
        model.addAttribute("appointmentDetail", appointment);
        model.addAttribute("allShifts", shift);
        return "appointments/update";
    }
    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("allAppointments",
                ObjectMapper.mapAll(appointmentClient.getAllAppointments().getBody(), AppointmentResponse.class));
        return ("appointments/list");
    }
}
