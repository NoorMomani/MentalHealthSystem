package com.example.MentalHealthSystem.repository;


import com.example.MentalHealthSystem.Database.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByAssessmentName(String assessmentName);
    List<Question> findByAssessmentId(Long assessmentId);
}
