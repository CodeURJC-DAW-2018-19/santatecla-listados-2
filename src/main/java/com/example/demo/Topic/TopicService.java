package com.example.demo.Topic;

import com.example.demo.Answer.Answer;
import com.example.demo.Answer.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    @Autowired
    private TopicRepository repository;

    public Optional<Topic> findOne(int id) {
        return repository.findById(id);
    }

    public Optional<Topic> findOne(String name) {
        return repository.findByName(name);
    }
    public List<Topic> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    public Page<Topic> findAll(@PageableDefault(size = 10) Pageable pageable) {
        return repository.findAll(pageable);
    }
    public List<Topic> findAll() {
        return repository.findAll();
    }

    public void save(Topic topic) {
        repository.save(topic);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
    public void delete(Topic t) {
        repository.delete(t);
    }
}