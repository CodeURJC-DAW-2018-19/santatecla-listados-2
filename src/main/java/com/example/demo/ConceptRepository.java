package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public  interface  ConceptRepository extends JpaRepository<Concept,Integer> {
    Concept findByName(String name);
    List<Concept> findByNameContainingOrTopicNameContaining(String name,String nameT);
    List<Concept> findByNameContaining(String name);
}
