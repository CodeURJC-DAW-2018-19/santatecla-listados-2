package com.example.demo.answer;

import com.example.demo.question.Question;
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
        this.openAnswer="Pregunta de checkbox";
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