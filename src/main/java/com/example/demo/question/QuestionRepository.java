package com.example.demo.question;

import com.example.demo.concept.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public  interface  QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findByConceptAndCorrected(Concept concept, boolean corrected);
    Question findByQuestion(String name);
    Question findById(int id);
}