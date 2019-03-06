package com.example.demo.question;

import com.example.demo.answer.Answer;
import com.example.demo.concept.Concept;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@Table(name="Questions")
public class Question {

    public interface BasicInfo{}
    public interface ConceptAndAnswer {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    @JsonView (BasicInfo.class)
    private int id;

    @Column(name = "Questions")
    @JsonView (BasicInfo.class)
    private String question;

    @Column(name = "Type")
    @JsonView (BasicInfo.class)
    private String type;

    @Column(name = "Corrected")
    @JsonView (BasicInfo.class)
    private boolean corrected;

    @ManyToOne
    @JsonView (ConceptAndAnswer.class)
    private Concept concept;

    @OneToOne (cascade= CascadeType.ALL)
    @JsonView (ConceptAndAnswer.class)
    private Answer answer;

    public Question(){}

    public Question(String question, String type, boolean corrected) {
        this.question = question;
        this.type = type;
        this.corrected = corrected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCorrected() {
        return corrected;
    }

    public void setCorrected(boolean corrected) {
        this.corrected = corrected;
    }
}
