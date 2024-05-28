package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.constants.DoctorStatus;
import com.example.MentalHealthSystem.repository.DoctorRepository;
import com.example.MentalHealthSystem.repository.UserLoginDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ValidatorServiceTest {

    @Mock
    UserLoginDetailsRepository userLoginDetailsRepository;
    @Mock
    DoctorRepository doctorRepository;
    @InjectMocks
    ValidatorService validatorService;
    @Test
    void isActiveDoctor() {
        String email = "test@gmail.com";
        doReturn(Optional.of(new Doctor())).when(doctorRepository).findByEmailAndStatus(email, DoctorStatus.APPROVED);
        boolean isActive = validatorService.isActiveDoctor(email);
        assertTrue(isActive);
    }

    @Test
    void isUsedEmail() {
        String email = "test@gmail.com";
        doReturn(Optional.of(new Doctor())).when(userLoginDetailsRepository).findByEmail(email);
        boolean isUsed = validatorService.isUsedEmail(email);
        assertTrue(isUsed);
    }
}