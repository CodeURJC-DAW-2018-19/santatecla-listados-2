package com.example.demo.conceptHeader;



public class ConceptHeader {
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