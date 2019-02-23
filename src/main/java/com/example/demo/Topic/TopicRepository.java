package com.example.demo.Topic;

import com.example.demo.Concept.Concept;
import com.example.demo.Topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public  interface  TopicRepository extends JpaRepository<Topic,Integer> {
    Topic findByName(String name);
    Topic findByConcepts(Concept concept);
    List<Topic> findByNameContaining(String name);
}

