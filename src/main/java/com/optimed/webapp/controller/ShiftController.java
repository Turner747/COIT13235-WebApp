package com.optimed.webapp.controller;

import com.optimed.webapp.feignclient.StaffClient;
import com.optimed.webapp.mappper.ObjectMapper;
import com.optimed.webapp.response.ShiftResponse;
import com.optimed.webapp.response.StaffResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/shifts")
public class ShiftController {
    @Autowired
    private StaffClient staffClient;
    @PostMapping(value = "/save", consumes = "*/*")
    public String saveShift(@ModelAttribute("shiftDetail") ShiftResponse shift,
                            Model model) {
        try {
            staffClient.saveShift(shift);
        } catch(Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "errors/shift-error"; // something needs fixing here
        }
        return "redirect:/shifts";
    }
    @GetMapping("/add")
    public String addShift(Model model) {
        List<StaffResponse> staffs = ObjectMapper.mapAll(staffClient.getAllStaffs().getBody(), StaffResponse.class);
        Collections.sort(staffs);
        model.addAttribute("shiftDetail", new ShiftResponse());
        model.addAttribute("allStaffs", staffs);
        return "shifts/add";
    }
    @GetMapping("/update/{id}")
    public String updateShift(@PathVariable("id") long id, Model model) {
        ShiftResponse shift = ObjectMapper.map(staffClient.getShiftById(id).getBody(), ShiftResponse.class);
        model.addAttribute("shiftDetail",shift);
        return "shifts/update";
    }
    @GetMapping("delete/{id}")
    public String deleteShift(@PathVariable("id") long id) {
        staffClient.deleteShiftById(id);
        return "redirect:/shifts";
    }
    @GetMapping
    public String listShift(Model model) {
        model.addAttribute("allShifts",
                ObjectMapper.mapAll(staffClient.getAllShifts().getBody(), ShiftResponse.class));
        return ("shifts/list");
    }
}
