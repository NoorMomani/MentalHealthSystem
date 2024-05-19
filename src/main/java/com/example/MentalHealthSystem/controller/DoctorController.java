package com.example.MentalHealthSystem.controller;

import com.example.MentalHealthSystem.Database.Appointment;
import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.Database.Patient;
import com.example.MentalHealthSystem.constants.UserRoles;
import com.example.MentalHealthSystem.request.DoctorSignUpRequest;
import com.example.MentalHealthSystem.request.SignUpRequest;
import com.example.MentalHealthSystem.service.DoctorService;
import com.example.MentalHealthSystem.service.SignUpService;
import jakarta.servlet.http.HttpServletRequest;
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
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @GetMapping("/dashboard")
    public String doctorDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Check if the user is logged in
        String username = userDetails.getUsername();
        if (username != null) {
            Doctor doctor = doctorService.getDoctorById(username);
            List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(doctor.getEmail());
            model.addAttribute("doctor", doctor);
            model.addAttribute("appointments", appointments);
            return "doctor_dashboard"; // Redirect to the doctor dashboard page
        } else {
            return "redirect:/login"; // Redirect to login if not logged in
        }
    }

    @GetMapping("/page/{email}")
    public String getDoctorProfile(@PathVariable String email, Model model) {
        Doctor doctor = doctorService.getDoctorById(email);
        model.addAttribute("doctor", doctor);
        List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(email);
        model.addAttribute("appointments", appointments);
        return "doctor_dashboard";
    }

    @PostMapping("/{email}/update")
    public String updateDoctorProfile(@PathVariable String email, @ModelAttribute DoctorSignUpRequest doctorSUR,
                                      HttpServletRequest request) {

        try {
            // Get the doctor by ID
            Doctor doctor = doctorService.getDoctorById(email);

            // Update the doctor's profile with the data from the form
            doctor.setName(doctorSUR.getName());
            doctor.setYearsOfExperience(doctorSUR.getYearsOfExperience());
            doctor.setCountry(doctorSUR.getCountry());
            doctor.setCity(doctorSUR.getCity());
            doctor.setJobTitle(doctorSUR.getJobTitle());
            doctor.setNationality(doctorSUR.getNationality());
            doctor.setPhoneNumber(doctorSUR.getPhoneNumber());
            doctor.setAddress(doctorSUR.getAddress());
            doctor.setGender(doctorSUR.getGender());
            doctor.setAge(doctorSUR.getAge());
            doctor.setSessionPrice(doctorSUR.getSessionPrice());
            doctor.setAboutTheDoctor(doctorSUR.getAboutTheDoctor());



            // Update the doctor in the database
            doctorService.updateDoctor(doctor);

            // Redirect back to the doctor's profile page
            return "redirect:/doctors/page/" + email;
        } catch (Exception e) {
            // Print the stack trace for debugging
            e.printStackTrace();
            // Redirect to an error page or handle the error appropriately
            return "login";
        }
    }

    @PostMapping("/{email}/appointments/add")
    public String addAppointment(@PathVariable String email,
                                 @RequestParam("appointmentDateTime") LocalDateTime appointmentDateTime) {

        doctorService.addAppointment(email, appointmentDateTime);
        return "redirect:/doctors/page/" + email;
    }
    @PostMapping("/{email}/appointments/{appointmentId}/update")
    public String updateAppointment(@PathVariable String email,
                                    @PathVariable Long appointmentId,
                                    @RequestParam("session") LocalDateTime session) {

        doctorService.updateAppointment(email, appointmentId, session);
        return "redirect:/doctors/page/" + email;
    }

    @PostMapping("/{email}/appointments/{appointmentId}/delete")
    public String deleteAppointment(@PathVariable String email,
                                    @PathVariable Long appointmentId) {

        doctorService.deleteAppointment(email, appointmentId);
        return "redirect:/doctors/page/" + email;
    }

}
