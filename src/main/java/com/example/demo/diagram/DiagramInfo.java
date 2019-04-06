package com.example.demo.diagram;

public class DiagramInfo {

    private String name;
    private int errors, hits, pendings;

    public DiagramInfo(String name, int errors, int hits, int pendings){
        super();
        this.name = name;
        this.errors = errors;
        this.hits = hits;
        this.pendings = pendings;
    }

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

}
