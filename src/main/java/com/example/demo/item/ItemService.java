package com.example.demo.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository repository;

    public Optional<Item> findOne(int id) {
        return repository.findById(id);
    }
    public Optional<Item> findOne(String name) {
        return repository.findByName(name);
    }
    public List<Item> findAll() {
        return repository.findAll();
    }
    public List<Item> findByConceptName(String name) {
        return repository.findByConceptName(name);
    }
    public List<Item> findCorrect(boolean b) {
        return repository.findByCorrect(b);
    }

    public void save(Item item) {
        repository.save(item);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
    public void delete(Item i) {
        repository.delete(i);
    }

    public Page<Item> findAll(Pageable page) {
        return repository.findAll(page);
    }

    public Page<Item> findAllByConceptId(int id,Pageable page) {
        return repository.findAllByConceptId(id,page);
    }
    public int getSizeConceptId(int id) {
        return repository.findAllByConceptId(id).size();
    }

}
