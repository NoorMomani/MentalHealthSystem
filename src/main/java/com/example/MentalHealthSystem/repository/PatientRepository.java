package com.example.MentalHealthSystem.repository;

import com.example.MentalHealthSystem.Database.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
   // Optional<Patient> findByEmail(String email);
}
