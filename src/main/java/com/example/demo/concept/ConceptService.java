package com.example.demo.concept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConceptService {
    @Autowired
    private ConceptRepository repository;

    public Optional<Concept> findOne(int id) {
        return repository.findById(id);
    }
    public Concept findOne(String name) {
        return repository.findByName(name);
    }
    public List<Concept> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    public List<Concept> findAll() {
        return repository.findAll();
    }

    public void save(Concept concept) {
        repository.save(concept);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
    public void delete(Concept c) {
        repository.delete(c);
    }
}
