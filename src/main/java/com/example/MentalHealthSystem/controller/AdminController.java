package com.example.MentalHealthSystem.controller;

import com.example.MentalHealthSystem.Database.Admin;
import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    AdminService adminService;
    @GetMapping("/dashboard")
    public String patientDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Check if the user is logged in
        String username = userDetails.getUsername();

        if (username != null) {
            Admin admin = adminService.getAdminById(username);
            model.addAttribute("admin", admin);
            return "admin_dashboard"; // Redirect to the patient dashboard page
        } else {
            return "redirect:/login"; // Redirect to login if not logged in
        }
    }

    @GetMapping("/AdminDash")
    public String AdminDash(Model model) {
        model.addAttribute("requestedDoctors", adminService.getRequestedDoctors());
        model.addAttribute("systemDoctors", adminService.getSystemDoctors());
        return "doctors";
    }

    @GetMapping("/doctorDetails/{email}")
    public String getDoctorDetails(@PathVariable String email, Model model) {
        Doctor doctor = adminService.getDoctorByEmail(email);
        model.addAttribute("doctor", doctor);
        return "DoctorDetails";
    }
    @GetMapping("/acceptDoctor/{email}")
    public String acceptDoctor(@PathVariable String email, Model model) {
        Doctor doctor = adminService.acceptDoctors(email);
        model.addAttribute("doctor", doctor);
        return "success";
    }
    @GetMapping("/rejectDoctor/{email}")
    public String rejectDoctor(@PathVariable String email, Model model) {
        Doctor doctor = adminService.rejectDoctors(email);
        model.addAttribute("doctor", doctor);
        return "success";
    }
    @PostMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("email") String email, Model model) {
        Doctor doctor = adminService.getDoctorByEmail(email);
        if (doctor != null) {
            // Send rejection email
            adminService.sendRejectionEmail(doctor);
            // Delete the specialist
            adminService.deleteDoctor(email);
            model.addAttribute("message", "Application rejected successfully.");
        } else {
            // Handle error, specialist not found
            model.addAttribute("message", "Error rejecting application.");
        }
        // Redirect to the list of existing doctors
        return "doctors";
    }
    @PostMapping("/searchRequestedDoctors")
    public String searchSubmittedDoctorsByEmail(@RequestParam String email, Model model) {
        List<Doctor> requestedDoctors = adminService.getRequestedDoctors();
        model.addAttribute("requestedDoctors", requestedDoctors);
        return "doctors"; // Redirect back to the doctors.html page
    }
    @GetMapping("/searchSystemDoctors")
    public String searchSystemDoctorsByEmail(@RequestParam String email, Model model) {
        Doctor doctor = adminService.getDoctorByEmail(email);
        model.addAttribute("systemDoctors", doctor);
        return "doctors"; // Redirect back to the doctors.html page
    }

    @PostMapping("/changePassword")
    public RedirectView changePassword(@RequestParam("email") String email,
                                       @RequestParam("newPassword") String newPassword,
                                       RedirectAttributes redirectAttributes) {
        adminService.changePassword(email, newPassword);
        redirectAttributes.addFlashAttribute("message", "Password changed successfully.");
        return new RedirectView("/doctorDetails?email=" + email);
    }

}
