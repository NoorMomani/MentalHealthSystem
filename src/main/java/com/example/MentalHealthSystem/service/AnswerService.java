package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.Answer;
import com.example.MentalHealthSystem.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    ///////////////////////////////////
    // Map ResultSet to Answer object
    public Answer getAnswersByQuestionId(int questionId) {
        return answerRepository.findByQuestionId(questionId).orElse(null);

    }
}