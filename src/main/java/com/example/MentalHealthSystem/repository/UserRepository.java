package com.example.MentalHealthSystem.repository;

import com.example.MentalHealthSystem.Database.Patient;
import com.example.MentalHealthSystem.Database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<Patient> findByEmail(String email);
}
