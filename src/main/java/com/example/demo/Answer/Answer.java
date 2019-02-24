package com.example.demo.Answer;

import com.example.demo.Question.Question;
import javax.persistence.*;

@Entity
@Table (name = "Answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    protected int id;
    @Column (name = "AnswersTest")
    private String answerTest;
    @Column (name = "OpenAnswer")
    private String openAnswer;
    @Column (name = "Marks")
    private boolean mark;
    @OneToOne
    private Question question;

    public Answer(){}

    public Answer(String answerTest, boolean mark) {
        this.answerTest = answerTest;
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
