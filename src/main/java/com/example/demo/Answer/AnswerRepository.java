package com.example.demo.Answer;

import com.example.demo.Question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface  AnswerRepository extends JpaRepository<Answer,Integer> {
    List<Answer> findByQuestion(Question question);
    Answer findByOpenAnswer(String name);
}
