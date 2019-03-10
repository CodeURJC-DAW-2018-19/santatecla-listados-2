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
@RequestMapping("/api/question")
public class QuestionRestController {
    @Autowired
    private ConceptService conceptService;
    @Autowired
    private QuestionService questionService;

    private final int DEFAULT_SIZE = 10;

    interface QuestionDetails extends Question.BasicInfo, Question.ConceptAndAnswer, Concept.BasicInfo, Answer.BasicInfo {}

    //Region Question

    @JsonView(QuestionDetails.class)
    @GetMapping("/all")
    public List<Question> getQuestions() {
        return questionService.findAll();
    }

    @JsonView(Question.BasicInfo.class)
    @GetMapping(value = "/all/pag")
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
    @RequestMapping(value = "/newQuestion", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Question newQuestion(@RequestBody Question question) {

        questionService.save(question);

        return question;
    }

    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/updateQuestion/{id}", method = RequestMethod.PUT)
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
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Question> deleteQuestion(@PathVariable int id) {
        Question question = questionService.findOne(id);

        if (question != null) {
            questionService.delete(id);
            return new ResponseEntity<>(question, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //New Question using URL parameters
    @JsonView(QuestionDetails.class)
    @PostMapping("/newQuestion/{conceptName}/{questionText}/{questionType}/{corrected}")
    public ResponseEntity<Question> newConcreteQuestion(@PathVariable String conceptName, @PathVariable String questionText, @PathVariable String questionType, @PathVariable boolean corrected) {
        Concept concept = conceptService.findOne(conceptName).get();

        if (concept != null) {
            Question question = new Question();
            question.setQuestion(questionText);
            question.setType(questionType);
            question.setCorrected(corrected);
            question.setConcept(concept);
            concept.setQuestion(question);

            conceptService.save(concept);
            return new ResponseEntity<>(question, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update Questions using  URL parameters
    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/updateText/{id}/{newText}", method = RequestMethod.PUT)
    public ResponseEntity<Question> updateQuestionText(@PathVariable int id, @PathVariable String newText) {
        Question question = questionService.findOne(id);

        if (question != null) {
            question.setQuestion(newText);
            questionService.save(question);
            return new ResponseEntity<>(question, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/updateCorrected/{id}/{corrected}", method = RequestMethod.PUT)
    public ResponseEntity<Question> updateQuestionCorrected(@PathVariable int id, @PathVariable boolean corrected) {
        Question question = questionService.findOne(id);

        if (question != null) {
            question.setCorrected(corrected);
            questionService.save(question);
            return new ResponseEntity<>(question, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // endregion
}

