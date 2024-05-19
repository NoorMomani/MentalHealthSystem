package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.Patient;
import com.example.MentalHealthSystem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Patient getPatientById(String id) {
        return patientRepository.findById(id).orElse(null);
    }

    public void updatePatient(Patient patient) {
        patientRepository.save(patient);
    }

}
