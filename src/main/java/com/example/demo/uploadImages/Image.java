package com.example.demo.uploadImages;


import com.example.demo.concept.Concept;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    private int id;
    @Column(name="Title")
    private String title;
    @Column (name="Base64",columnDefinition="MEDIUMBLOB")
    private String base64;
    @OneToOne
    private Concept concept;

    public Image() {
    }

    public Image(String title, String imag) {
        this.title = title;
        this.base64=imag;
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


    public String getBase64() {
        return base64;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public Concept getRelatedconcept(){ return concept; }

    public void setRelatedconcept(Concept concept){ this.concept = concept; }

    @Override
    public String toString() {
        return "Image [id=" + id + ", title=" + title + "]";
    }

}

