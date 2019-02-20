package com.example.demo.Question;

import com.example.demo.Question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public  interface  QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findByQuestion(String question);
}