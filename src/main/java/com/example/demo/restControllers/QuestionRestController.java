package com.example.demo.restControllers;

import com.example.demo.answer.Answer;
import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.item.Item;
import com.example.demo.item.ItemService;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class    QuestionRestController {
    @Autowired
    private ConceptService conceptService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ItemService itemService;


    private final int DEFAULT_SIZE = 10;

    interface QuestionDetails extends Question.BasicInfo, Question.ConceptAndAnswer, Concept.BasicInfo, Concept.BasicInfoGuest, Answer.BasicInfo {}

    //Region Question

    @JsonView(Question.BasicInfo.class)
    @GetMapping(value = "/")
    public Page<Question> getTopics(@PageableDefault(size = DEFAULT_SIZE) Pageable page) {
        Page<Question> questions = questionService.findAll(page);
        return questions;
    }

    @JsonView(Question.BasicInfo.class)
    @GetMapping(value = "/concept/{id}/{corrected}")
    public Page<Question> getQuestionsByID(@PageableDefault(size = DEFAULT_SIZE) Pageable page,@PathVariable int id,
                                           @PathVariable boolean corrected) {
        page = PageRequest.of(page.getPageNumber(),10);
        return questionService.findByConcept_IdAndCorrected(id,page,corrected);
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

    /*@JsonView(QuestionDetails.class)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Question newQuestion(@RequestBody Question question) {

        questionService.save(question);

        return question;
    }*/


    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Question newQuestion(@PathVariable int id) {
        Concept concept = conceptService.findOne(id).get();
        int typeItem = 0;
        List<Item> list = new ArrayList<>();
        int typeQuestion = (int)(Math.random() * 4);
        Question question;
        String questionName = "";
        if (typeQuestion == 0) {
            questionName = "¿Cuáles son " + concept.getName() + " ?";

        } else if (typeQuestion == 1) {
            List<Item> itemsList = itemService.findByConceptName(concept.getName());
            typeItem = (int) (Math.random() * itemsList.size() - 1);
            questionName = "¿" + itemsList.get(typeItem).getName() + " es un elemento de " + concept.getName() + " ?";

        } else if (typeQuestion == 2) {
            List<Item> itemsList = itemService.findCorrect(true);
            typeItem = (int) (Math.random() * itemsList.size() - 1);
            itemsList.remove(typeItem);
            String otherItems = "";
            for (Item i : itemsList) {
                otherItems += i.getName() + ", ";
            }
            questionName = "¿Qué elemento falta en " + otherItems + " para completar la lista de " + concept.getName() + " ?";

        } else if (typeQuestion == 3) {
            List<Item> itemsList = itemService.findByConceptName(concept.getName());
            for (Item i : itemsList) {
                System.out.println(i.getName());
            }
            String string = "";
            for (int i = 0; i < 2; i++) {
                typeItem = (int) (Math.random() * itemsList.size() - 1);
                list.add(itemsList.get(typeItem));
                string += itemsList.get(typeItem).getName() + ", ";
                itemsList.remove(typeItem);
            }
            questionName = "¿Qué elementos de " + string + " no son parte de " + concept.getName() + " ?";
        }
        if (typeQuestion == 0. || typeQuestion == 2) {
            question = new Question(questionName, "" + typeQuestion, false);
        } else {
            question = new Question(questionName, "" + typeQuestion, true);
        }

        concept.setQuestion(question);
        question.setConcept(concept);
        questionService.save(question);
        conceptService.save(concept);
        return question;
    }
    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Question> updateQuestion(@PathVariable int id, @RequestBody Question updatedQuestion) {
        Question question = questionService.findOne(id);
        if (question.isCorrected()){
            question.getConcept().setHits(question.getConcept().getHits()+1);
            question.getConcept().setPendings(question.getConcept().getPendings()-1);
            question.getConcept().getTopic().setHits(question.getConcept().getTopic().getHits()+1);
            question.getConcept().getTopic().setPendings(question.getConcept().getTopic().getPendings()-1);
        }else{
            question.getConcept().setErrors(question.getConcept().getErrors()+1);
            question.getConcept().setPendings(question.getConcept().getPendings()-1);
            question.getConcept().getTopic().setErrors(question.getConcept().getTopic().getErrors()+1);
            question.getConcept().getTopic().setPendings(question.getConcept().getTopic().getPendings()-1);
        }
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

