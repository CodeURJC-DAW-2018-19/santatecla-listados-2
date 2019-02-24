package com.example.demo.Topic;

import com.example.demo.Concept.Concept;
import com.example.demo.Topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public  interface  TopicRepository extends JpaRepository<Topic,Integer> {
    Optional<Topic> findByName(String name);
    List<Topic> findByNameContaining(String name);
}

