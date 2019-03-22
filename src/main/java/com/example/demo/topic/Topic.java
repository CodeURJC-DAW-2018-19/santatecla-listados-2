package com.example.demo.topic;

import com.example.demo.concept.Concept;
import org.hibernate.annotations.Cascade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Topic {

    public interface BasicInfo{}
    public interface BasicInfoGuest{}
    public interface ConceptList{}

    @JsonView(BasicInfo.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    private int id;

    @Column(name="Name")
    @JsonView(BasicInfoGuest.class)
    private String name;
    @Column(name="Errors")
    @JsonView(BasicInfo.class)
    private int errors;
    @Column(name ="Hits")
    @JsonView(BasicInfo.class)
    private int hits;
    @Column(name="Pendings")
    @JsonView(BasicInfo.class)
    private int pendings;





    @OneToMany(cascade = CascadeType.ALL,mappedBy = "topic")
    @JsonView(ConceptList.class)
    private Set<Concept> concepts;

    public Topic(String name,int number) {
        this.name = ("Tema"+ number + ": "+name);
        concepts=new LinkedHashSet<>();
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

    public void setConcepts(LinkedHashSet<Concept> concepts) {
        this.concepts = concepts;
    }
    public void setConcept(Concept c){
        concepts.add(c);
    }
    public void removeConcept(Concept c){
        concepts.remove(c);
    }
    public void removeConcepts(){
        concepts=new LinkedHashSet<>();
    }

}
