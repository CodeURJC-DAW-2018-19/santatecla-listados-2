package com.example.demo.restControllers;

import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.item.Item;
import com.example.demo.question.Question;
import com.example.demo.topic.Topic;
import com.example.demo.topic.TopicService;
import com.example.demo.user.UserComponent;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concepts")
public class ConceptRestController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private ConceptService conceptService;
    @Autowired
    private UserComponent userComponent;

    private static final int DEFAULT_SIZE = 10;

    interface ConceptDetails extends Concept.BasicInfo, Concept.BasicInfoGuest, Concept.ObjectLists, Concept.RelatedTopic, Topic.BasicInfo, Topic.BasicInfoGuest, Item.BasicInfo, Question.BasicInfo{}
    interface ConceptDetailsGuest extends Concept.BasicInfoGuest, Concept.RelatedTopic, Topic.BasicInfoGuest{}

    //Region Concepts

    @GetMapping(value = "/")
    public MappingJacksonValue getConcepts(@PageableDefault(size = DEFAULT_SIZE) Pageable page) {
        MappingJacksonValue concepts = new MappingJacksonValue(this.conceptService.findAll(page));
        if (userComponent.isLoggedUser()){
            concepts.setSerializationView(ConceptDetails.class);
        } else {
            concepts.setSerializationView(ConceptDetailsGuest.class);
        }
        return concepts;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Concept> getConcept(@PathVariable int id) {
        if (!conceptService.findOne(id).isPresent())
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(conceptService.findOne(id).get(),HttpStatus.OK);
    }

    @JsonView(ConceptDetails.class)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Concept> newConcept(@RequestBody Concept concept) {
        String name = concept.getName();
        if (conceptService.findOne(name).isPresent())
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        conceptService.save(concept);
        return new ResponseEntity<>(concept, HttpStatus.CREATED);
    }

    @JsonView(ConceptDetails.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Concept> updateConcept(@PathVariable int id, @RequestBody Concept updatedConcept){

        Concept concept = conceptService.findOne(id).get();

        if(concept != null){
            updatedConcept.setId(id);
            conceptService.save(updatedConcept);
            return new ResponseEntity<>(updatedConcept, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(ConceptDetails.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Concept> deleteConcept(@PathVariable int id) {
        if (!conceptService.findOne(id).isPresent())
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Concept concept =conceptService.findOne(id).get();
        conceptService.delete(concept);
        return new ResponseEntity<>(concept,HttpStatus.OK);
    }
    //endregion
}
