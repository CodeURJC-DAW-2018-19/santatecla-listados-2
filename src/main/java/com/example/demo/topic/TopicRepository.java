package com.example.demo.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public  interface  TopicRepository extends JpaRepository<Topic,Integer> {
    Optional<Topic> findByName(String name);
    List<Topic> findByNameContaining(String name);

    //Page<Topic> findAll(Pageable page);
}

