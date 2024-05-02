package com.example.MentalHealthSystem.controller;

import com.example.MentalHealthSystem.request.SignUpRequest;
import com.example.MentalHealthSystem.service.SignUpService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    @GetMapping("/signup") // Handle GET requests for /signup
    public String showSignUpForm(Model model) {
        model.addAttribute("patient", new SignUpRequest());
        return "SignUp"; // Assuming you have a Thymeleaf template named SignUp.html
    }

    @PostMapping("/signup/user")
    public String signUp(@ModelAttribute("patient") SignUpRequest request) {
        if (!signUpService.signUp(request.getEmail(), request.getPassword()))
            return "login_failed";
        return "signup_success"; // Show signup form again with error message
    }
}
