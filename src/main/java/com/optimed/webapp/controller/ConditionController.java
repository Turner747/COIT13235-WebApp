
package com.optimed.webapp.controller;

import com.optimed.webapp.feignclient.PatientClient;
import com.optimed.webapp.feignclient.StaffClient;
import com.optimed.webapp.mappper.ObjectMapper;
import com.optimed.webapp.response.ConditionResponse;
import com.optimed.webapp.response.PatientResponse;
import com.optimed.webapp.response.StaffResponse;
import com.optimed.webapp.response.VisitNoteResponse;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conditions")
public class ConditionController {
    
    @Autowired
    private PatientClient patientClient;
    
    @Autowired
    StaffClient staffClient;
    
    @PostMapping(value = "/save", consumes = "*/*")
    public String saveCondition(@ModelAttribute("conditionDetail") ConditionResponse condition,
                            Model model) {
        try {
            patientClient.saveCondition(condition);
        } catch(Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "errors/visit-note-error"; // something needs fixing here
        }
        return "redirect:/conditions";
    }
    
    @GetMapping("/add/{id}")
    public String addCondition(@PathVariable("id") Long id, Model model) {
        PatientResponse patient = ObjectMapper.map(patientClient.getPatientById(id).getBody(), PatientResponse.class);
        StaffResponse doctor = ObjectMapper.map(staffClient.getStaffByID(id).getBody(), StaffResponse.class);
        
        model.addAttribute("conditionDetail", new ConditionResponse());
        model.addAttribute("patientDetail", patient);
        model.addAttribute("staffDetails", doctor);
        
        return "conditions/add";
    }
    
    @GetMapping("/add")
    public String addConditions(Model model) {
        List<PatientResponse> patients = ObjectMapper.mapAll(patientClient.getAllPatients().getBody(), PatientResponse.class);
        Collections.sort(patients);
        List<StaffResponse> doctors = ObjectMapper.mapAll(staffClient.getAllDoctors().getBody(), StaffResponse.class);
        Collections.sort(doctors);

        ConditionResponse condition = new ConditionResponse();
        condition.setDiagnosisDate(new Date());
        model.addAttribute("allPatients", patients);
        model.addAttribute("allDoctors", doctors);
        model.addAttribute("conditionDetail", condition);
        return "conditions/add";
    }
    
    @GetMapping("/update/{id}")
    public String updateCondition(@PathVariable("id") Long id, Model model) {
        ConditionResponse condition = ObjectMapper.map(patientClient.getConditionById(id).getBody(), ConditionResponse.class);
                
        PatientResponse patient = ObjectMapper.map(patientClient.getPatientById(condition.getPatient().getId()).getBody(), PatientResponse.class);
        StaffResponse doctor = ObjectMapper.map(staffClient.getStaffByID(condition.getDoctor().getId()).getBody(), StaffResponse.class);
        
        model.addAttribute("conditionDetail", condition);
        model.addAttribute("patientDetail", patient);
        model.addAttribute("staffDetails", doctor);
        
        return "conditions/update";
    }
    
    @GetMapping("delete/{id}")
    public String deleteCondition(@PathVariable("id") Long id) {
        patientClient.deleteConditionById(id);
        return "redirect:/conditions";
    }
    
    @GetMapping
    public String listConditions(Model model) {
        model.addAttribute("allConditions",
                ObjectMapper.mapAll(patientClient.getAllConditions().getBody(), ConditionResponse.class));
        return ("conditions/list");
    }
}