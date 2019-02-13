package com.example.demo;

public class Concept {
    private String name;
    private String html;
    private int errors;
    private int hits;
    private int pendings;

    public Concept(String name, String html) {
        this.name = name;
        this.html = html;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
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

}
