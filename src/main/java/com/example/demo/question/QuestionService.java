package com.example.demo.question;

import com.example.demo.concept.Concept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository repository;

    public Question findOne(String name) {
        return repository.findByQuestion(name);
    }
    public List<Question> findByConceptAndCorrected (Concept concept, boolean corrected){
        return repository.findByConceptAndCorrected(concept,corrected);
    }

    public List<Question> findAll() {
        return repository.findAll();
    }

    public void save(Question question) {
        repository.save(question);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
    public void delete(Question q) {
        repository.delete(q);
    }

    public Question findOne(int id) {return repository.findById(id);}

    public Page<Question> findAll(Pageable page) {
        return repository.findAll(page);
    }

    public Page<Question> findByConcept_Id(int id, Pageable page) {
        return repository.findByConcept_Id(id,page);
    }

}
