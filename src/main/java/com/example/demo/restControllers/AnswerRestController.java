package com.example.demo.restControllers;

import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerService;
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
@RequestMapping("/api/answers")
public class AnswerRestController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    private final int DEFAULT_SIZE = 10;


    interface AnswerDetails extends Answer.BasicInfo, Answer.AnswerObject, Question.BasicInfo{}


    //Region Answer

    @JsonView(AnswerDetails.class)
    @GetMapping("/all")
    public List<Answer> getAnswers(){
        return answerService.findAll();
    }

    @JsonView(Answer.BasicInfo.class)
    @GetMapping(value = "/all/pag")
    public Page<Answer> getTopics(@PageableDefault(size = DEFAULT_SIZE) Pageable page) {
        return answerService.findAll(page);
    }

    @JsonView(AnswerDetails.class)
    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswer(@PathVariable int id){
        Answer answer = answerService.findOne(id);

        if(answer != null){
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Answer newAnswer(@RequestBody Answer answer) {

        answerService.save(answer);

        return answer;
    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Answer> updateAnswer(@PathVariable int id, @RequestBody Answer updatedAnswer){
        Answer answer = answerService.findOne(id);

        if(answer != null){
            updatedAnswer.setId(id);
            answerService.save(updatedAnswer);
            return new ResponseEntity<>(updatedAnswer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/question{questionId}/deleteAnswer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Answer> deleteAnswer(@PathVariable int id, @PathVariable int questionId) {
        Question question = questionService.findOne(questionId);
        Answer answer = answerService.findOne(id);
        if (question != null) {
            if (answer != null) {
                question.setAnswer(null);
                answerService.delete(id);
                return new ResponseEntity<>(answer, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //New Answers using URL parameters
    @JsonView(AnswerDetails.class)
    @PostMapping("/{questionId}")
    public ResponseEntity<Answer> newConcreteAnswer(@PathVariable int questionId, @RequestBody String openAnswer, @RequestBody boolean mark){

        Question question = questionService.findOne(questionId);

        if (question != null) {
            Answer answer = new Answer();
            answer.setOpenAnswer(openAnswer);
            answer.setMark(mark);
            answer.setQuestion(question);
            question.setAnswer(answer);
            questionService.save(question);
            return new ResponseEntity<>(answer, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update Answers using  URL parameters
    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/o/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Answer> updateOpenAnswer(@PathVariable int id,@RequestBody String openAnswer){
        Answer answer = answerService.findOne(id);

        if(answer != null){
            answer.setOpenAnswer(openAnswer);
            answerService.save(answer);
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/m/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Answer> updateAnswerMark(@PathVariable int id, @RequestBody boolean mark){
        Answer answer = answerService.findOne(id);

        if(answer != null){
            answer.setMark(mark);
            answerService.save(answer);
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //endregion
}

