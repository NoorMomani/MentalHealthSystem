package com.example.MentalHealthSystem.repository;


import com.example.MentalHealthSystem.Database.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor_Email(String doctorId);
    List<Appointment> findByDoctor_EmailAndBooked(String doctorId, boolean booked);
    List<Appointment> findByPatient_Email(String patientId); // Add this method
}
