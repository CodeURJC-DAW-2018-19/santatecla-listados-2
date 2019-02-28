package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User>  findByName(String name);
    List<User> findBySurname(String surname);
    User findByUsername(String userName);
}
