package com.example.demo.Question;

import com.example.demo.Concept.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public  interface  QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findByQuestion(String question);

    List<Question> findByConceptAndCorrected(Concept concept, boolean corrected);
}