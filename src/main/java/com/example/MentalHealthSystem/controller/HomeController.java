package com.example.MentalHealthSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(value = "/home")
    public String home() {
        String actionPage = "home";
        return "home";
    }
    @GetMapping(value = "/doctor/home")
    public String doctorLogin() {
        String actionPage = "doctor_home";
        return "doctor_home";
    }
    @GetMapping(value = "/patient/home")
    public String login() {
        String actionPage = "patient_home";
        return "home";
    }
}
