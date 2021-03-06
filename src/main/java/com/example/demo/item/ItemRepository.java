package com.example.demo.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public  interface  ItemRepository extends JpaRepository<Item,Integer> {
    Optional<Item>findByName(String name);
    List<Item> findByConceptName(String name);
    List<Item> findByCorrect(Boolean b);
    Optional<Item> findById(int id);

    Page<Item> findAll(Pageable page);
    Page<Item> findAllByConceptId(int id,Pageable page);
    List<Item> findAllByConceptId(int id);

}
