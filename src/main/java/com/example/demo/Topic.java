package com.example.demo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    private int id;
    @Column(name="Name")
    private String name;
    @Column(name="Errors")
    private int errors;
    @Column(name ="Hits")
    private int hits;
    @Column(name="Pendings")
    private int pendings;
    @OneToMany(cascade= CascadeType.ALL ,mappedBy = "topic")
    private Set<Concept> concepts;
    public Topic(String name) {
        this.name = name;
        concepts=new HashSet<>();
    }
    public Topic(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getPendings() {
        return pendings;
    }

    public void setPendings(int pendings) {
        this.pendings = pendings;
    }

    public int getTotal() {
        return this.getErrors()+this.getHits()+this.getPendings();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(Set<Concept> concepts) {
        this.concepts = concepts;
    }
    public void setConcept(Concept c){
        concepts.add(c);
    }

}
