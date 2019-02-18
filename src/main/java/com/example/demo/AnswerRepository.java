package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public  interface  AnswerRepository extends JpaRepository<Answer,Integer> {
    List<Answer> findByQuestion(Question question);
}
