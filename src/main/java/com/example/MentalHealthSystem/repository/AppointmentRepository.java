package com.example.MentalHealthSystem.repository;


import com.example.MentalHealthSystem.Database.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorEmail(String doctorId);
    List<Appointment> findByDoctorIdAndBooked(String doctorId, boolean booked);
    List<Appointment> findByPatientEmail(String patientId); // Add this method
}
