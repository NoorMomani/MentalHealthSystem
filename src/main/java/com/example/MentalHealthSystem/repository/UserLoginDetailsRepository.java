package com.example.MentalHealthSystem.repository;

import com.example.MentalHealthSystem.Database.UserLoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginDetailsRepository extends JpaRepository<UserLoginDetails, String> {
    Optional<UserLoginDetails> findByEmail(String email);
}
