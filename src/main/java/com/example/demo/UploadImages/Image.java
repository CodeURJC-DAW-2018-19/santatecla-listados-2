package com.example.demo.UploadImages;


import com.example.demo.Concept.Concept;

import javax.persistence.*;

//@Entity
public class Image {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name="PrimaryKey")
    private int pk;
    //@Column(name="Id")
    private int id;
    //@Column(name="Title")
    private String title;

    //@Column(name="Concept")
    private String concept;

    //@OneToOne
    //private Concept relatedconcept;




    public Image(int id, String title, String concept) {
        this.id = id;
        this.title = title;
        this.concept = concept;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConcept() { return concept; }

    public void setConcept(String concept) { this.concept = concept; }

    //public Concept getRelatedconcept(){ return relatedconcept; }

    //public void setRelatedconcept(Concept concept){ this.relatedconcept = concept; }

    @Override
    public String toString() {
        return "Image [id=" + id + ", title=" + title + "]";
    }

}

