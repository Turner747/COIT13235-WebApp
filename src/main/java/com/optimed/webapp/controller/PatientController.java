
package com.optimed.webapp.controller;

import com.optimed.webapp.feignclient.PatientClient;
import com.optimed.webapp.mappper.ObjectMapper;
import com.optimed.webapp.response.PatientResponse;
import com.optimed.webapp.response.StaffResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@Controller
@RequestMapping("/patients")
public class PatientController {
    
    @Autowired
    private PatientClient patientClient;
    
    @GetMapping("/register")
    public String registerPatientPage(Model model) {
        model.addAttribute("patientDetail", new PatientResponse());
        //Collection<RoleResponse> roles = ObjectMapper.mapAll(patientClient.getAllRoles().getBody(), RoleResponse.class);
        //model.addAttribute("allRoles", roles);
        return "patients/register";
    }

    //@GetMapping("/role/{name}")
    //public String getRoleByName(@PathVariable("name") String name, Model model) {
        //RoleResponse role = ObjectMapper.map(staffClient.getRoleByName(name).getBody(), RoleResponse.class);
        //model.addAttribute("role", role);
        //return "role";
    //}
    
    @PostMapping(value = "/save", consumes = "*/*")
    public String savePatient(@ModelAttribute("patient") PatientResponse patient,
                            Model model) {
        try {
            patientClient.savePatient(patient);
        } catch(Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "errors/patient-error"; //Attention needed here!!
        }
        return "redirect:/patients";
    }
    
    @GetMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") long id, Model model) {
        PatientResponse patient = ObjectMapper.map(patientClient.getPatientById(id).getBody(), PatientResponse.class);
        //Collection<RoleResponse> roles = ObjectMapper.mapAll(staffClient.getAllRoles().getBody(), RoleResponse.class);
        model.addAttribute("patientDetail",patient);
        //model.addAttribute("allRoles", roles);
        return "patients/update";
    }
    
    @GetMapping
    public String listPatient(Model model) {
        model.addAttribute("allPatients",
                ObjectMapper.mapAll(patientClient.getAllPatients().getBody(), PatientResponse.class));
        return "patients/list";
    }
    
}
