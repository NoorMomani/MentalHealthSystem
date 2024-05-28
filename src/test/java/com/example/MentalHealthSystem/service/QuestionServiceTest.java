package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.Answer;
import com.example.MentalHealthSystem.Database.Question;
import com.example.MentalHealthSystem.repository.AnswerRepository;
import com.example.MentalHealthSystem.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    QuestionService questionService;

    @Test
    void getQuestionsByAssessmentIdWhenIdInDBThenReturnQuestionList() {
        Question question = new Question();
        List<Question> questionList = List.of(question) ;
        doReturn(Optional.of(questionList)).when(questionRepository).findByAssessmentId(3L);
        assertEquals(questionList, questionService.getQuestionsByAssessmentId(3L));
    }
    @Test
    void getQuestionsByAssessmentIdWhenIdNotInDBThenReturnNull() {
        assertEquals(List.of(), questionService.getQuestionsByAssessmentId(3L));
    }
}