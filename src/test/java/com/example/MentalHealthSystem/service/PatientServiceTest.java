package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.*;
import com.example.MentalHealthSystem.repository.AppointmentRepository;
import com.example.MentalHealthSystem.repository.PatientRepository;
import com.example.MentalHealthSystem.repository.ReportRepository;
import com.example.MentalHealthSystem.request.PatientSignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private LocationService locationService;

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
        Patient patient = new Patient();
        PatientSignUpRequest patientSUR = new PatientSignUpRequest();

        doReturn(Optional.of(patient)).when(patientRepository).findById("test");
        when(patientRepository.save(patient)).thenReturn(patient);

        patientService.updatePatientProfile("test", patientSUR);
        verify(patientRepository, times(1)).save(patient);

    }

    @Test
    void bookAppointment() {
    }

    @Test
    void getAppointmentsByPatientEmail() {
        Appointment appointment = new Appointment();
        List<Appointment> appointmentList = List.of(appointment) ;
        doReturn(appointmentList).when(appointmentRepository).findByDoctorEmail("test");
        assertEquals(appointmentList, patientService.getAppointmentsByPatientEmail("test"));
    }

    @Test
    void getAppointmentsByDoctorEmailAndBookedIsTrue() {
        Appointment appointment = new Appointment();
        List<Appointment> appointmentList = List.of(appointment) ;
        doReturn(appointmentList).when(appointmentRepository).findByDoctorEmailAndBooked("test",true);
        assertEquals(appointmentList, patientService.getAppointmentsByDoctorEmailAndBookedIsTrue("test"));
    }

    @Test
    void deleteExpiredAppointments() {
    }

    @Test
    void cancelAppointment() {
    }

    @Test
    void addReport() {
    }

    @Test
    void getReportsForPatient() {
        Appointment appointment = new Appointment();
        appointment.setBooked(true);
        appointment.setId(1L);
        List<Appointment> appointmentList = List.of(appointment);
        Report report = new Report();
        List<Report> reports = List.of(report);
        doReturn(appointmentList).when(appointmentRepository).findByPatientEmail("test");
        doReturn(reports).when(reportRepository).findByAppointmentIn(appointmentList);
        assertEquals(reports, patientService.getReportsForPatient("test"));
    }
}