package com.example.demo.Item;

import com.example.demo.Item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface  ItemRepository extends JpaRepository<Item,Integer> {
    List<Item> findByName(String name);
}
