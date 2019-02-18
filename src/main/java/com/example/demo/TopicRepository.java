package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public  interface  TopicRepository extends JpaRepository<Topic,Integer> {
    List<Topic> findByName(String name);
}

