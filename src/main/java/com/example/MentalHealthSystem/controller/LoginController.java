package com.example.MentalHealthSystem.controller;

import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    DoctorService doctorService;
    @GetMapping(value = "/login")
    public String login1() {
        //  String actionPage = "patient_login";
        return "loginPage";
    }
    @GetMapping(value = "/homepage")
    public String homepage(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "homepage";
    }
    @GetMapping(value = "/")
    public String login() {
        //  String actionPage = "patient_login";
        return "loginPage";
    }

    @GetMapping(value = "/location")
    public String location() {
        //  String actionPage = "patient_login";
        return "location";
    }

    @GetMapping("/login_failed")
    public String authenticationFailed(Model model) {
        return "login_failed";
    }

}
