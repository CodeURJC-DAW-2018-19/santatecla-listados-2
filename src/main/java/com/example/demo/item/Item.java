package com.example.demo.item;

import com.example.demo.concept.Concept;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@Table(name = "Items")
public class Item {

    public interface BasicInfo{}
    public interface ItemConcept {}

    @JsonView(BasicInfo.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    protected int id;

    @Column (name = "Name")
    @JsonView(BasicInfo.class)
    private String name;

    @Column (name = "Correct")
    @JsonView(BasicInfo.class)
    private boolean correct;

    @ManyToOne
    @JsonView(ItemConcept.class)
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

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }
}
