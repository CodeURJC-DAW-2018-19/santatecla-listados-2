package com.example.demo.answer;

import com.example.demo.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface  AnswerRepository extends JpaRepository<Answer,Integer> {
    List<Answer> findByQuestion(Question question);
    Answer findByOpenAnswer(String name);
    Answer findById(int id);
}
