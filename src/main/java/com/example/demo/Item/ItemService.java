package com.example.demo.Item;

import com.example.demo.Answer.Answer;
import com.example.demo.Answer.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void save(Item item) {
        repository.save(item);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
    public void delete(Item i) {
        repository.delete(i);
    }
}
