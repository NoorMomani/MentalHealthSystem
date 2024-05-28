package com.example.MentalHealthSystem.repository;

import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.constants.DoctorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,String> {
    Optional<Doctor> findByEmail(String email);
    Optional<List<Doctor>> findAllByStatus(DoctorStatus status);
    Optional<Doctor> findByEmailAndStatus(String email, DoctorStatus status);
}
