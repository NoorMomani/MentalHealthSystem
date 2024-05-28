package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.Answer;
import com.example.MentalHealthSystem.Database.Patient;
import com.example.MentalHealthSystem.repository.PatientRepository;
import com.example.MentalHealthSystem.request.PatientSignUpRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @Mock
    PatientRepository patientRepository;


    @InjectMocks
    PatientService patientService;

    @Test
    void getPatientByIdWhenIdInDBThenReturnPatientObject() {
        Patient patient = new Patient();
        doReturn(Optional.of(patient)).when(patientRepository).findById("test");
        assertEquals(patient, patientService.getPatientById("test"));
    }

    @Test
    void getPatientByIdWhenIdNotInDBThenReturnNull() {
        assertNull(patientService.getPatientById("test"));
    }


    @Test
    void updatePatientProfile() {

    }

    @Test
    void bookAppointment() {
    }

    @Test
    void getAppointmentsByPatientEmail() {
    }

    @Test
    void getAppointmentsByDoctorEmailAndBookedIsTrue() {
    }

    @Test
    void deleteExpiredAppointments() {
    }

    @Test
    void sendConfirmationEmail() {
    }

    @Test
    void cancelAppointment() {
    }

    @Test
    void addReport() {
    }

    @Test
    void getReportsForPatient() {
    }
}