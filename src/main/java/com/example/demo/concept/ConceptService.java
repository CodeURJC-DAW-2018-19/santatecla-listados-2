package com.example.demo.concept;

import com.example.demo.diagram.DiagramInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConceptService {
    @Autowired
    private ConceptRepository repository;

    //public Optional <Concept> findoptOne(int id) {
      //  return repository.findByIdopt(id);
    //}

    /*public Optional <Concept> findoptOne(String name) {
        return repository.findByNameopt(name);
    }*/


    public Optional<Concept> findOne(int id) {
        return repository.findById(id);
    }
    public Optional<Concept> findOne(String name) {
        return repository.findByName(name);
    }
    public List<Concept> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }


    public List<Concept> findAll() {
        return repository.findAll();
    }

    public List<Concept> findTopicName(String name){return repository.findByTopicName(name);}

    public void save(Concept concept) {
        repository.save(concept);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
    public void delete(Concept c) {
        repository.delete(c);
    }

    public Page<Concept> findAll(Pageable page) {
        return repository.findAll(page);
    }

}
