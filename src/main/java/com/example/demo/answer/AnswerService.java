package com.example.demo.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository repository;

    public Answer findOne(int id) {
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

    public Page<Answer> findAll(Pageable page) {
        return repository.findAll(page);
    }
}
