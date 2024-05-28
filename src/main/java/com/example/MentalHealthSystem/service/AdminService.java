package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.Admin;
import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.Database.UserLoginDetails;
import com.example.MentalHealthSystem.constants.DoctorStatus;
import com.example.MentalHealthSystem.repository.AdminRepository;
import com.example.MentalHealthSystem.repository.DoctorRepository;
import com.example.MentalHealthSystem.repository.UserLoginDetailsRepository;
import com.example.MentalHealthSystem.request.AdminSignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, DoctorRepository doctorRepository, JavaMailSender emailSender,
                        UserLoginDetailsRepository userLoginDetailsRepository , PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.emailSender = emailSender;
        this.passwordEncoder = passwordEncoder;
        this.userLoginDetailsRepository = userLoginDetailsRepository;
    }

    public void updatePassword(String email, String newPassword) {
        UserLoginDetails userLoginDetails = userLoginDetailsRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        userLoginDetails.setPassword(passwordEncoder.encode(newPassword));
        userLoginDetailsRepository.save(userLoginDetails);
        sendNewPasswordEmail(email, newPassword);
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
    public Optional<Doctor> findPendingDoctorByEmail(String email) {

        return doctorRepository.findByEmailAndStatus(email, DoctorStatus.PENDING);
    }
    public Optional<Doctor> findApprovedDoctorByEmail(String email) {

        return doctorRepository.findByEmailAndStatus(email, DoctorStatus.APPROVED);
    }
    public List<Doctor> getSystemDoctors()
    {
//        return doctorRepository.findAll();
        Optional<List<Doctor>> requestedDoctors = doctorRepository.findAllByStatus(DoctorStatus.APPROVED);
        log.error(requestedDoctors.get().toString());
        return requestedDoctors.orElseGet(List::of);
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
            sendAcceptanceEmail(doctor);
            return doctorRepository.save(doctor);
        } else {
            throw new RuntimeException("Doctor email does not exist");
        }
    }

    public void sendAcceptanceEmail(Doctor doctor) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(doctor.getEmail());
        message.setSubject("Your application has been accepted");
        message.setText("Dear " + doctor.getName() + ",\n\n"
                + "Congratulations! Your application has been accepted. "
                + "You can now log in using your email and  password "
                + "\n\nBest regards,\nThe Admin Team");
        emailSender.send(message);
    }

    public void deleteDoctor(String email) {
        doctorRepository.deleteById(email);
    }
    public void sendDeleteEmail(Doctor doctor) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(doctor.getEmail());
        message.setSubject("Your account has been deleted");
        message.setText("Dear " + doctor.getName() + ",\n\n"
                + "We regret to inform you that your account has been deleted."
                + "\n\nBest regards,\nThe Admin Team");
        emailSender.send(message);
    }
    public Doctor rejectDoctors(String email) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(email);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setStatus(DoctorStatus.DECLINED);
            sendRejectEmail(doctor);
            return doctorRepository.save(doctor);
        } else {
            throw new RuntimeException("Doctor email does not exist");
        }
    }
    public void sendRejectEmail(Doctor doctor) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(doctor.getEmail());
        message.setSubject("Your application has been rejected");
        message.setText("Dear " + doctor.getName() + ",\n\n"
                + "We regret to inform you that your application has been rejected."
                + "\n\nBest regards,\nThe Admin Team");
        emailSender.send(message);
    }

        public void changePassword(String email, String newPassword) {

            log.error("new password before");
            Optional<UserLoginDetails> optionalUserLoginDetails = userLoginDetailsRepository.findByEmail(email);

            if (optionalUserLoginDetails.isPresent()) {
                UserLoginDetails userLoginDetails = optionalUserLoginDetails.get();
                log.error("new password after {}", userLoginDetails.getPassword());
                userLoginDetails.setPassword(newPassword);
                userLoginDetailsRepository.save(userLoginDetails);
                log.error("new password {}", userLoginDetails.getPassword());
//                log.error("new password {}", adminRepository.getReferenceById(email).get);
                sendNewPasswordEmail(email, newPassword);
            } else {
                throw new RuntimeException("Doctor email not exist");
            }
    }

    public void updateAdmin(String email, AdminSignUpRequest adminSUR ){
        Admin admin = adminRepository.findById(email).orElse(null);
        if (admin != null) {
            admin.setName(adminSUR.getName());
            admin.setNationality(adminSUR.getNationality());
            admin.setPhoneNumber(adminSUR.getPhoneNumber());
            admin.setGender(adminSUR.getGender());
            admin.setAge(adminSUR.getAge());
            admin.setCountry(adminSUR.getCountry());
            admin.setCity(adminSUR.getCity());
            adminRepository.save(admin);
        } else {
            throw new RuntimeException("Admin not found with email: " + email);
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

