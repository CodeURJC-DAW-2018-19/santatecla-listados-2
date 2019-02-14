package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface  ConceptRepository extends JpaRepository<Concept,Integer> {
    List<Concept> findByName(String name);
}
