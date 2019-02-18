package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User>  findByName(String name);
    List<User> findBySurname(String surname);
    User findByUsername(String userName);
}
