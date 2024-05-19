package com.example.MentalHealthSystem.controller;

import com.example.MentalHealthSystem.constants.UserRoles;
import com.example.MentalHealthSystem.request.AdminSignUpRequest;
import com.example.MentalHealthSystem.request.DoctorSignUpRequest;
import com.example.MentalHealthSystem.request.PatientSignUpRequest;
import com.example.MentalHealthSystem.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("signup")
public class SignUpController {

    @Autowired
    SignUpService signUpService;
    @GetMapping("/patients")
    public String patientsSignUp(Model model) {
        model.addAttribute("patientSignUpRequest", new PatientSignUpRequest());
        return "signup_patient";
    }

    @PostMapping("/patient_signup")
    public String signUpPatient(@ModelAttribute("patientSignUpRequest") PatientSignUpRequest request) {
        request.setUserRole(UserRoles.PATIENT);
        if (!signUpService.signUp(request)) {
            return "signup_failed";
        }
        return "loginPage";
    }

    @GetMapping("/doctors")
    public String doctorsSignUp(Model model) {
        model.addAttribute("doctorSignUpRequest", new DoctorSignUpRequest());
        return "signup_doctor";
    }

    @PostMapping("/doctor_signup")
    public String signUpDoctor(@ModelAttribute("doctorSignUpRequest") DoctorSignUpRequest request) {
        request.setUserRole(UserRoles.DOCTOR);

        if (!signUpService.signUp(request)) {
            return "signup_failed";
        }
        return "loginPage";
    }
    @GetMapping("/admins")
    public String adminsSignUp(Model model) {
        model.addAttribute("adminSignUpRequest", new AdminSignUpRequest());
        return "signup_admin";
    }

    @PostMapping("/admin_signup")
    public String signUpAdmin(@ModelAttribute("adminSignUpRequest") AdminSignUpRequest request) {
        request.setUserRole(UserRoles.ADMIN);

        if (!signUpService.signUp(request)) {
            return "signup_failed";
        }
        return "loginPage";
    }
}
