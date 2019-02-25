package com.example.demo.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository repository;

    public Optional<Answer> findOne(int id) {
        return repository.findById(id);
    }
    public Answer findOne(String name) {
        return repository.findByOpenAnswer(name);
    }

    public List<Answer> findAll() {
        return repository.findAll();
    }

    public void save(Answer answer) {
        repository.save(answer);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
