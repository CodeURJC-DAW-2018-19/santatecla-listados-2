package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public  interface  QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findByQuestion(String question);
}
