package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.*;
import com.example.MentalHealthSystem.constants.DoctorStatus;
import com.example.MentalHealthSystem.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdminService {
    AdminRepository adminRepository;

    DoctorRepository doctorRepository;

    private final JavaMailSender emailSender;

    UserLoginDetailsRepository userLoginDetailsRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, DoctorRepository doctorRepository, JavaMailSender emailSender,
                        UserLoginDetailsRepository userLoginDetailsRepository) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.emailSender = emailSender;
        this.userLoginDetailsRepository = userLoginDetailsRepository;
    }

    public Admin getAdminById(String id) {
        return adminRepository.findById(id).orElse(null);
    }

    public List<Doctor> getRequestedDoctors()
    {
        Optional<List<Doctor>> requestedDoctors = doctorRepository.findAllByStatus(DoctorStatus.PENDING);
        log.error(requestedDoctors.get().toString());
        return requestedDoctors.orElseGet(List::of);

    }
    public List<Doctor> getSystemDoctors()
    {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorByEmail(String email)
    {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(email);
        if (optionalDoctor.isPresent()){
            return optionalDoctor.get();
        } else {
            throw new RuntimeException("Doctor email not exist");
        }
    }
    public Doctor acceptDoctors(String email) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(email);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setStatus(DoctorStatus.APPROVED);
            return doctorRepository.save(doctor);
        } else {
            throw new RuntimeException("Doctor email does not exist");
        }
    }
    public Doctor rejectDoctors(String email) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(email);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setStatus(DoctorStatus.DECLINED);
            return doctorRepository.save(doctor);
        } else {
            throw new RuntimeException("Doctor email does not exist");
        }
    }


    public void deleteDoctor(String email) {
        doctorRepository.deleteById(email);
    }
    public void sendRejectionEmail(Doctor doctor) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(doctor.getEmail());
        message.setSubject("Your account has been deleted");
        message.setText("Dear " + doctor.getName() + ",\n\n"
                + "We regret to inform you that your account has been deleted."
                + "\n\nBest regards,\nThe Admin Team");
        emailSender.send(message);
    }

        public void changePassword(String email, String newPassword) {

            Optional<UserLoginDetails> optionalUserLoginDetails = userLoginDetailsRepository.findByEmail(email);
            if (optionalUserLoginDetails.isPresent()) {
                UserLoginDetails userLoginDetails = optionalUserLoginDetails.get();
                userLoginDetails.setPassword(newPassword);
                userLoginDetailsRepository.save(userLoginDetails);
                sendNewPasswordEmail(email, newPassword);
            } else {
                throw new RuntimeException("Doctor email not exist");
            }
    }



    private void sendNewPasswordEmail(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("New Password");
        message.setText("Your new password is: " + newPassword);
        emailSender.send(message);
    }

}

