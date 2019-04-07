package com.example.demo.question;

import com.example.demo.concept.Concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public  interface  QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findByConceptAndCorrected(Concept concept, boolean corrected);
    Question findByQuestion(String name);
    Question findById(int id);

    Page<Question> findAll(Pageable page);
    Page<Question> findAllByConceptIdAndCorrectedFalse(int id,Pageable page);
    Page<Question> findByConceptIdAndCorrected(int id,Pageable page, boolean corrected);
    List<Question> findAllByConceptIdAndCorrectedFalse(int id);
    List<Question> findAllByConceptIdAndCorrected(int id,boolean b);
}