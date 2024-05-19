package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.Appointment;
import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.repository.AppointmentRepository;
import com.example.MentalHealthSystem.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Doctor getDoctorById(String id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public void updateDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    public List<Appointment> getAppointmentsByDoctorId(String doctorId) {
        String sql = "SELECT * FROM appointment WHERE doctor_id = ?";
        return jdbcTemplate.query(sql, new Object[]{doctorId}, (rs, rowNum) -> {
            Appointment appointment = new Appointment();
            appointment.setId(rs.getLong("id"));

            // Create a Doctor object and set its ID
            Doctor doctor = new Doctor();
            doctor.setEmail(doctorId);
            appointment.setDoctor(doctor);

            appointment.setSession(rs.getTimestamp("session").toLocalDateTime());
            // Map other columns as needed
            return appointment;
        });
    }


    public void updateAppointment(String doctorId, Long appointmentId,
                                  LocalDateTime session) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null && appointment.getDoctor().getEmail().equals(doctorId)) {
            appointment.setSession(session);
            appointmentRepository.save(appointment);
        }
    }

    public void deleteAppointment(String doctorId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null && appointment.getDoctor().getEmail().equals(doctorId)) {
            appointmentRepository.delete(appointment);
        }
    }

    public void addAppointment(String doctorId, LocalDateTime appointmentDateTime) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor != null) {
            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setSession(appointmentDateTime);
            appointmentRepository.save(appointment);
        }
    }

}
