package com.example.demo.concept;


import com.example.demo.item.Item;
import com.example.demo.question.Question;
import com.example.demo.topic.Topic;
import com.example.demo.uploadImages.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Concepts")
public class Concept {

    public interface BasicInfo{}
    public interface ObjectLists{}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    @JsonView(BasicInfo.class)
    private int id;

    @Column(name="Name")
    @JsonView(BasicInfo.class)
    private String name;

    @Column(name="html")
    @JsonView(BasicInfo.class)
    private String html;

    @Column(name="Errors")
    @JsonView(BasicInfo.class)
    private int errors;

    @Column(name="Hits")
    @JsonView(BasicInfo.class)
    private int hits;

    @Column(name = "Pendings")
    @JsonView(BasicInfo.class)
    private int pendings;

    @ManyToOne
    @JsonView(ObjectLists.class)
    private Topic topic;

    @OneToMany(cascade= CascadeType.ALL ,mappedBy = "concept")
    //@JsonView(ObjectLists.class)
    @JsonIgnore
    private Set<Question> questions;

    @OneToMany(cascade= CascadeType.ALL ,mappedBy = "concept")
    @JsonView(ObjectLists.class)
    private Set<Item> items;

    @OneToMany(cascade= CascadeType.ALL ,mappedBy = "concept")
    //@JsonView(ObjectLists.class)
    @JsonIgnore
    private List<Image> images;

    public Concept(){
        this.questions = new HashSet<>();
    }

    public Concept(String name, String html) {
        this.name = name;
        this.html = html;
        this.questions = new HashSet<>();
        this.items = new HashSet<>();
        this.images=new ArrayList<>();
    }
    public Concept(String name) {
        this.name = name;
        this.questions = new HashSet<>();
        this.items = new HashSet<>();
        this.images=new ArrayList<>();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public void setQuestion (Question question){
        this.questions.add(question);
    }

    public void setItem (Item item){
        this.items.add(item);
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImage(Image image) {
        this.images.add(image);
    }
}
