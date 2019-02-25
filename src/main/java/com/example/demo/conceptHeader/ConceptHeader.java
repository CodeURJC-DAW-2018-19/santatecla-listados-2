package com.example.demo.conceptHeader;


import javax.persistence.*;


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