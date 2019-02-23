package com.example.demo.UploadImages;

public class Image {

    private int id;
    private String title;
    private String concept;


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

    @Override
    public String toString() {
        return "Image [id=" + id + ", title=" + title + "]";
    }

}

