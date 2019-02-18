package com.example.demo;

import javax.persistence.*;

@Entity
@Table(name = "Items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    protected int id;
    @Column (name = "Name")
    private String name;
    @Column (name = "Correct")
    private boolean correct;
    @ManyToOne
    private Concept concept;

    public Item(){}

    public Item(String name, boolean correct) {
        this.name = name;
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

}
