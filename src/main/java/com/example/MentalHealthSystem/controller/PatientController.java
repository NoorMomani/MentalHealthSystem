package com.example.MentalHealthSystem.controller;

import com.example.MentalHealthSystem.Database.Patient;
import com.example.MentalHealthSystem.request.PatientSignUpRequest;
import com.example.MentalHealthSystem.service.PatientService;
import com.example.MentalHealthSystem.service.SignUpService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;

@Controller
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    PatientService patientService;

    @GetMapping("/assessments")
    public String assessment(){
        return "redirect:/assessments";
    }
    @GetMapping("/dashboard")
    public String patientDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Check if the user is logged in
        String username = userDetails.getUsername();

        if (username != null) {
            Patient patient = patientService.getPatientById(username);
            model.addAttribute("patient", patient);
            return "patient_dashboard"; // Redirect to the patient dashboard page
        } else {
            return "redirect:/login"; // Redirect to login if not logged in
        }
    }

    @GetMapping("/page/{email}")
    public String getPatientProfile(@PathVariable String email, Model model) {
        Patient patient = patientService.getPatientById(email);
        model.addAttribute("patient", patient);
        // Add other necessary attributes
        return "patient_dashboard";
    }
    @PostMapping("/{email}/update")
    public String updatePatientProfile(@PathVariable String email, @ModelAttribute PatientSignUpRequest patientSUR) {

        try {
            // Get the patient by ID
            Patient patient = patientService.getPatientById(email);

            // Update the patient's profile with the data from the form
            patient.setName(patientSUR.getName());
            patient.setNationality(patientSUR.getNationality());
            patient.setPhoneNumber(patientSUR.getPhoneNumber());
            patient.setAddress(patientSUR.getAddress());
            patient.setGender(patientSUR.getGender());
            patient.setAge(patientSUR.getAge());
            patient.setCountry(patientSUR.getCountry());
            patient.setCity(patientSUR.getCity());



            // Update the patient in the database
            patientService.updatePatient(patient);

            // Redirect back to the patient's profile page
            return "redirect:/patients/page/" + email;
        } catch (Exception e) {
            // Print the stack trace for debugging
            e.printStackTrace();
            // Redirect to an error page or handle the error appropriately
            return "login";
        }
    }


}
