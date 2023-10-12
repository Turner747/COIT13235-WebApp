package com.optimed.webapp.controller;

import com.optimed.webapp.feignclient.StaffClient;
import com.optimed.webapp.mappper.ObjectMapper;
import com.optimed.webapp.response.RoleResponse;
import com.optimed.webapp.response.StaffResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/staffs")
public class StaffController {
    @Autowired
    private StaffClient staffClient;
    @GetMapping("/register")
    public String registerStaffPage(Model model) {
        model.addAttribute("staffDetail", new StaffResponse());
        Collection<RoleResponse> roles = ObjectMapper.mapAll(staffClient.getAllRoles().getBody(), RoleResponse.class);
        model.addAttribute("allRoles", roles);
        return "staffs/register";
    }

    @GetMapping("/role/{name}")
    public String getRoleByName(@PathVariable("name") String name, Model model) {
        RoleResponse role = ObjectMapper.map(staffClient.getRoleByName(name).getBody(), RoleResponse.class);
        model.addAttribute("role", role);
        return "role";
    }
    @PostMapping(value = "/save", consumes = "*/*")
    public String saveStaff(@ModelAttribute("staff") StaffResponse staff,
                            Model model) {
        try {
            staffClient.saveStaff(staff);
        } catch(Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "errors/staff-error"; // something needs fixing here
        }
        return "redirect:/staffs";
    }
    @GetMapping("/update/{id}")
    public String updateStaff(@PathVariable("id") long id, Model model) {
        StaffResponse staff = ObjectMapper.map(staffClient.getStaffByID(id).getBody(), StaffResponse.class);
        Collection<RoleResponse> roles = ObjectMapper.mapAll(staffClient.getAllRoles().getBody(), RoleResponse.class);
        model.addAttribute("staffDetail",staff);
        model.addAttribute("allRoles", roles);
        return "staffs/update";
    }
    @GetMapping
    public String listStaff(Model model) {
        model.addAttribute("allStaffs",
                ObjectMapper.mapAll(staffClient.getAllStaffs().getBody(), StaffResponse.class));
        return "staffs/list";
    }
}

