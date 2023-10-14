
package com.optimed.webapp.controller;

import com.optimed.webapp.feignclient.PatientClient;
import com.optimed.webapp.feignclient.StaffClient;
import com.optimed.webapp.mappper.ObjectMapper;
import com.optimed.webapp.response.AppointmentResponse;
import com.optimed.webapp.response.PatientResponse;
import com.optimed.webapp.response.ShiftResponse;
import com.optimed.webapp.response.StaffResponse;
import com.optimed.webapp.response.VisitNoteResponse;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/visit-notes")
public class VisitNoteController {
    
    @Autowired
    private PatientClient patientClient;
    
    @Autowired
    private StaffClient staffClient;
   
    @GetMapping("/add")
    public String addVisitNote(Model model) {
        
        model.addAttribute("visitNoteDetail", new VisitNoteResponse());
        
        Collection<PatientResponse> patients = ObjectMapper.mapAll(patientClient.getAllPatients().getBody(), PatientResponse.class);
        model.addAttribute("allPatients", patients);
        
        Collection<StaffResponse> doctors = ObjectMapper.mapAll(staffClient.getAllStaffs().getBody(), StaffResponse.class);
        model.addAttribute("allDoctors", doctors);
        
        return "visit-notes/add";
    }
    
    @PostMapping(value = "/save", consumes = "*/*")
    public String saveVisitNote(@ModelAttribute("visitNote") VisitNoteResponse visitNote,
                            Model model) {
        try {
            patientClient.saveVisitNote(visitNote);
        } catch(Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "errors/visit-note-error"; // something needs fixing here
        }
        return "redirect:/visit-notes";
    }
    
    /*
    @GetMapping("/select-doctor")
    public String selectDoctor(Model model) {
        List<StaffResponse> doctors = ObjectMapper.mapAll(staffClient.getAllDoctors().getBody(), StaffResponse.class);
        Collections.sort(doctors);
        //List<ShiftResponse> shifts = new ArrayList<ShiftResponse>();
        //for(StaffResponse doctor : doctors) {
        //    shifts.addAll( ObjectMapper.mapAll(staffClient.getShiftByStaffId(doctor.getId()).getBody(), ShiftResponse.class) );
        //}
        //model.addAttribute("allShifts", shifts);
        return "visit-notes/select-doctor";
    }
    
    @GetMapping("/select-patient")
    public String selectPatient(Model model) {
        List<PatientResponse> patients = ObjectMapper.mapAll(patientClient.getAllPatients().getBody(), PatientResponse.class);
        //Collections.sort(patients);
        //List<ShiftResponse> shifts = new ArrayList<ShiftResponse>();
        //for(StaffResponse doctor : doctors) {
        //    shifts.addAll( ObjectMapper.mapAll(staffClient.getShiftByStaffId(doctor.getId()).getBody(), ShiftResponse.class) );
        //}
       // model.addAttribute("allShifts", shifts);
        return "visit-notes/select-patient";
    }
*/
    
    @GetMapping("/update/{id}")
    public String updateVisitNote(@PathVariable("id") Long id, Model model) {
        
        VisitNoteResponse visitNote = ObjectMapper.map(patientClient.getVisitNoteById(id).getBody(), VisitNoteResponse.class);
        model.addAttribute("visitNoteDetail", visitNote);
        
        Collection<PatientResponse> patients = ObjectMapper.mapAll(patientClient.getAllPatients().getBody(), PatientResponse.class);
        model.addAttribute("allPatients", patients);
        
        Collection<StaffResponse> doctors = ObjectMapper.mapAll(staffClient.getAllStaffs().getBody(), StaffResponse.class);
        model.addAttribute("allDoctors", doctors);
        
        return "visit-notes/update";
    }
    
    @GetMapping("delete/{id}")
    public String deleteVisitNote(@PathVariable("id") Long id) {
        patientClient.deleteVisitNoteById(id);
        return "redirect:/visit-notes";
    }
    
    @GetMapping
    public String listVisitNotes(Model model) {
        model.addAttribute("allVisitNotes",
                ObjectMapper.mapAll(patientClient.getAllVisitNotes().getBody(), VisitNoteResponse.class));
        return ("visit-notes/list");
    }
}