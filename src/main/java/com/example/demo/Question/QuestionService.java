package com.example.demo.Question;

import com.example.demo.Concept.Concept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository repository;

    public Optional<Question> findOne(int id) {
        return repository.findById(id);
    }
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
}
