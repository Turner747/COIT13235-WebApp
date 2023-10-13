package com.optimed.webapp.feignclient;

import com.optimed.webapp.response.ConditionResponse;
import com.optimed.webapp.response.PatientResponse;
import com.optimed.webapp.response.ShiftResponse;
import com.optimed.webapp.response.VisitNoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@FeignClient(name="patient-service")
public interface PatientClient {
    
    //Patient
    @PostMapping(value = "patients", produces = "application/json", consumes = "application/json")
    public ResponseEntity<PatientResponse> savePatient(@RequestBody PatientResponse patient);
    
    @GetMapping("patients")
    public ResponseEntity<Collection<PatientResponse>> getAllPatients();
    
    @GetMapping("patients/id/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable("id") Long id);
    
    //VisitNote
    @PostMapping(value = "visit-notes", produces = "application/json", consumes = "application/json")
    public ResponseEntity<VisitNoteResponse> saveVisitNote(@RequestBody VisitNoteResponse visitNote);
    
    @GetMapping("visit-notes")
    public ResponseEntity<Collection<VisitNoteResponse>> getAllVisitNotes();
    
    @GetMapping("visit-notes/id/{id}")
    public ResponseEntity<VisitNoteResponse> getVisitNoteById(@PathVariable("id") Long id);
    
    @GetMapping(value = "visit-notes/delete/{id}")
    public ResponseEntity<VisitNoteResponse> deleteVisitNoteById(@PathVariable("id") Long id);
    
    //Condition
    @PostMapping(value = "conditions", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ConditionResponse> saveCondition(@RequestBody ConditionResponse condition);
    
    @GetMapping("conditions")
    public ResponseEntity<Collection<ConditionResponse>> getAllConditions();
    
    @GetMapping("conditions/id/{id}")
    public ResponseEntity<ConditionResponse> getConditionById(@PathVariable("id") Long id);
    
    @GetMapping(value = "conditions/delete/{id}")
    public ResponseEntity<ConditionResponse> deleteConditionById(@PathVariable("id") Long id);
}
