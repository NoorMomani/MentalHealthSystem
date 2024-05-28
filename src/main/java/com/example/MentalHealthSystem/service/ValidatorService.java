package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.constants.DoctorStatus;
import com.example.MentalHealthSystem.repository.DoctorRepository;
import com.example.MentalHealthSystem.repository.PatientRepository;
import com.example.MentalHealthSystem.repository.UserLoginDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    UserLoginDetailsRepository userLoginDetailsRepository;



    public boolean isActiveDoctor(String email) {
        return doctorRepository.findByEmailAndStatus(email, DoctorStatus.APPROVED).isPresent();
    }

    public boolean isUsedEmail(String email) {
        return userLoginDetailsRepository.findByEmail(email).isPresent();
    }
}
