package com.example.demo.restControllers;

import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerService;
import com.example.demo.concept.Concept;
import com.example.demo.item.Item;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionService;
import com.example.demo.topic.Topic;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/answers")
public class AnswerRestController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    private final int DEFAULT_SIZE = 10;


    interface AnswerDetails extends Answer.BasicInfo, Answer.AnswerObject, Question.BasicInfo {
    }


    //Region Answer

    @JsonView(Answer.BasicInfo.class)
    @GetMapping(value = "/")
    public Page<Answer> getAnswer(@PageableDefault(size = DEFAULT_SIZE) Pageable page) {
        return answerService.findAll(page);
    }

    @JsonView(AnswerDetails.class)
    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswer(@PathVariable int id) {
        Answer answer = answerService.findOne(id);

        if (answer != null) {
            return new ResponseEntity<>(answer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Answer> newAnswer(@RequestBody String answer, @PathVariable int id) {
        Question question = this.questionService.findOne(id);
        Answer answer1 = new Answer();
        answer1.setQuestion(question);
        answer1.setOpenAnswer(answer);
        if (question.getType() == 0 || question.getType() == 2) {

        } else if (question.getType() == 1) {
            boolean correct;
            if (answer.equals("VERDADERO")){
                correct = true;
            }else{
                correct =false;
            }
            question.setCorrected(true);
            Concept c = question.getConcept();
            Topic topic = c.getTopic();
            Set<Item> itemSet = question.getConcept().getItems();
            Item selected = new Item();
            for (Item item :
                    itemSet) {
                if (question.getQuestion().contains(item.getName())) {
                    selected = item;
                }
            }
            if (selected.isCorrect() && correct) {
                answer1.setMark(true);
                c.setHits(c.getHits() + 1);
                topic.setHits(topic.getHits() + 1);
            } else {
                answer1.setMark(false);
                c.setErrors(c.getErrors() + 1);
                topic.setErrors(topic.getErrors() + 1);
            }

        } else if (question.getType() == 3) {
            question.setCorrected(true);
            String[] items = answer.split("add");
            ArrayList<String> items1= new ArrayList<>(Arrays.asList(items));
            StringBuilder respuesta = new StringBuilder();
            for (String s : items1){
                respuesta.append(s).append(", ");
            }
            String realanswer = respuesta.toString();
            answer1.setOpenAnswer(realanswer);
            Concept c = question.getConcept();
            Topic topic = c.getTopic();
            int result = 0;

            for (String s: items1) {
                for (Item i: question.getConcept().getItems()) {
                    if(i.getName().equals(s) && i.isCorrect()){
                        result++;
                    }
                }
            }

            if(result==c.getItems().size()){
                answer1.setMark(true);
                c.setHits(c.getHits()+1);
                topic.setHits(topic.getHits()+1);
            }else {
                answer1.setMark(false);
                c.setErrors(c.getErrors() + 1);
                topic.setErrors(topic.getErrors() + 1);
            }

        }
        question.setAnswer(answer1);
        answerService.save(answer1);
        return new ResponseEntity<>(answer1, HttpStatus.CREATED);

    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Answer> updateAnswer(@PathVariable int id, @RequestBody Answer updatedAnswer) {
        Answer answer = answerService.findOne(id);

        if (answer != null) {
            updatedAnswer.setId(id);
            answerService.save(updatedAnswer);
            return new ResponseEntity<>(updatedAnswer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/question{questionId}/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Answer> deleteAnswer(@PathVariable int id, @PathVariable int questionId) {
        Question question = questionService.findOne(questionId);
        Answer answer = answerService.findOne(id);
        if (question != null) {
            if (answer != null) {
                question.setAnswer(null);
                answerService.delete(id);
                return new ResponseEntity<>(answer, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //endregion
}

