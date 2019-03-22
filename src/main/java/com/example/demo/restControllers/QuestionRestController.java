package com.example.demo.restControllers;

import com.example.demo.answer.Answer;
import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class    QuestionRestController {
    @Autowired
    private ConceptService conceptService;
    @Autowired
    private QuestionService questionService;

    private final int DEFAULT_SIZE = 10;

    interface QuestionDetails extends Question.BasicInfo, Question.ConceptAndAnswer, Concept.BasicInfo, Concept.BasicInfoGuest, Answer.BasicInfo {}

    //Region Question

    @JsonView(Question.BasicInfo.class)
    @GetMapping(value = "/")
    public Page<Question> getTopics(@PageableDefault(size = DEFAULT_SIZE) Pageable page) {
        Page<Question> questions = questionService.findAll(page);
        return questions;
    }

    @JsonView(QuestionDetails.class)
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable int id) {

        Question question = questionService.findOne(id);

        if (question != null) {
            return new ResponseEntity<>(question, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Question newQuestion(@RequestBody Question question) {

        questionService.save(question);

        return question;
    }

    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Question> updateQuestion(@PathVariable int id, @RequestBody Question updatedQuestion) {
        Question question = questionService.findOne(id);

        if (question != null) {
            updatedQuestion.setId(id);
            questionService.save(updatedQuestion);
            return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Question> deleteQuestion(@PathVariable int id) {
        Question question = questionService.findOne(id);

        if (question != null) {
            questionService.delete(id);
            return new ResponseEntity<>(question, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // endregion
}

