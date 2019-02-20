package com.example.demo.Question;

import com.example.demo.Answer.Answer;
import com.example.demo.Concept.Concept;

import javax.persistence.*;

@Entity
@Table(name="Questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    private int id;
    @Column(name = "Questions")
    private String question;
    @Column(name = "Type")
    private String type;
    @Column(name = "Corrected")
    private boolean corrected;
    @ManyToOne
    private Concept concept;
    @OneToOne (cascade= CascadeType.ALL)
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
