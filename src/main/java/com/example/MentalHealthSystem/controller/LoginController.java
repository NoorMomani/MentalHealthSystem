package com.example.MentalHealthSystem.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @GetMapping(value = "/login")
    public String login() {
      //  String actionPage = "patient_login";
        return "loginPage";
    }


    @GetMapping("/login_failed")
    public String authenticationFailed(Model model) {
        return "login_failed";
    }

}
