package com.example.demo.ConceptHeader;


import com.example.demo.Concept.Concept;
import com.example.demo.Item.Item;
import com.example.demo.Question.Question;
import com.example.demo.Topic.Topic;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ConceptsHeader")
public class ConceptHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    private int id;
    @Column(name="Name")
    private String name;

    public ConceptHeader(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        ConceptHeader c = (ConceptHeader)obj;
        return this.name.equals(c.getName());
    }
}