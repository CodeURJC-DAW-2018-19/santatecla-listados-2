package com.example.demo.Concept;

import com.example.demo.Concept.Concept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface  ConceptRepository extends JpaRepository<Concept,Integer> {
    Concept findByName(String name);
    List<Concept> findByNameContainingOrTopicNameContaining(String name,String nameT);
    List<Concept> findByNameContaining(String name);
}
