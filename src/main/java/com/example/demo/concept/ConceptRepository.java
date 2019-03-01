package com.example.demo.concept;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public  interface  ConceptRepository extends JpaRepository<Concept,Integer> {
    Concept findByName(String name);
    List<Concept> findByNameContaining(String name);
}