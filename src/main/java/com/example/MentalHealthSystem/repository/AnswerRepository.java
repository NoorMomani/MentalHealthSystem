package com.example.MentalHealthSystem.repository;

import com.example.MentalHealthSystem.Database.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    Optional<Answer> findByQuestionId(int questionId);
}