package com.example.demo.answer;

import com.example.demo.question.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@Table (name = "Answers")
public class Answer {

    public interface BasicInfo{}
    public interface AnswerObject {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    @JsonView(BasicInfo.class)
    protected int id;

    @Column (name = "AnswersTest")
    @JsonIgnore
    private String answerTest;

    @Column (name = "OpenAnswer")
    @JsonView(BasicInfo.class)
    private String openAnswer;

    @Column (name = "Marks")
    @JsonView(BasicInfo.class)
    private boolean mark;

    @OneToOne
    @JsonView(AnswerObject.class)
    private Question question;

    public Answer(){}

    public Answer(String openAnswer, boolean mark) {
        this.openAnswer = openAnswer;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswerTest() {
        return answerTest;
    }

    public String getOpenAnswer() {
        return openAnswer;
    }

    public void setOpenAnswer(String openAnswer) {
        this.openAnswer = openAnswer;
    }

    public void setAnswerTest(String answerTest) {
        this.answerTest = answerTest;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }
}
