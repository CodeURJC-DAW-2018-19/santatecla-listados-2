package com.example.demo.concept;

import com.example.demo.answer.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public  interface  ConceptRepository extends JpaRepository<Concept,Integer> {
    //Concept findByName(String name);
    Optional <Concept> findByName(String name);

    List<Concept> findByNameContaining(String name);
    List<Concept> findByTopicName(String name);

    Optional<Concept> findById(int id);

    Page<Concept> findAll(Pageable page);
}
