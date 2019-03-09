package com.example.demo.restControllers;

import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.item.Item;
import com.example.demo.question.Question;
import com.example.demo.topic.Topic;
import com.example.demo.topic.TopicService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concept")
public class ConceptRestController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private ConceptService conceptService;

    interface ConceptDetails extends Concept.BasicInfo, Concept.ObjectLists, Topic.BasicInfo, Item.BasicInfo, Question.BasicInfo{}


    //Region Concepts

    @JsonView(ConceptDetails.class)
    @GetMapping("/all")
    public ResponseEntity<List<Concept>> getConcepts() {
        return new ResponseEntity<>(conceptService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Concept> getConcept(@PathVariable int id) {
       // if (!conceptService.findoptOne(id).isPresent())
         //   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(conceptService.findOne(id),HttpStatus.OK);
    }

    @JsonView(ConceptDetails.class)
    @RequestMapping(value = "/newConcept", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Concept newConcept(@RequestBody Concept concept) {

        conceptService.save(concept);

        return concept;
    }

    @JsonView(ConceptDetails.class)
    @RequestMapping(value = "/updateConcept/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Concept> updateConcept(@PathVariable int id, @RequestBody Concept updatedConcept){

        Concept concept = conceptService.findOne(id);

        if(concept != null){
            updatedConcept.setId(id);
            conceptService.save(updatedConcept);
            return new ResponseEntity<>(updatedConcept, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Concept> deleteConcept(@PathVariable int id) {
       // if (!conceptService.findoptOne(id).isPresent())
         //   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Concept concept =conceptService.findOne(id);
        conceptService.delete(concept);
        return new ResponseEntity<>(concept,HttpStatus.OK);
    }

    //New Concept using URL parameters
    @PostMapping("/newConcept/{name}/{topicname}")
    public ResponseEntity<Concept> newConcreteConcept(@PathVariable String name, @PathVariable String topicname) {
       /* if (conceptService.findoptOne(name).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);*/
        Concept concept = new Concept();
        concept.setName(name);
        concept.setTopic(topicService.findOne(topicname).get());
        topicService.findOne(topicname).get().setConcept(concept);
        concept.setErrors(0);
        concept.setPendings(0);
        concept.setHits(0);
        conceptService.save(concept);
        return new ResponseEntity<>(concept,HttpStatus.OK);
    }

    //Update Concept using  URL parameters
    @RequestMapping(value = "/{id}/newName/{name}", method = RequestMethod.PUT)
    public ResponseEntity<Concept> updateConceptName(@PathVariable int id, @PathVariable String name) {
        /*if (!conceptService.findoptOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);*/
        Concept concept = conceptService.findOne(id);
        concept.setName(name);
        conceptService.save(concept);
        return new ResponseEntity<>(concept,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/topic/{topic}", method = RequestMethod.PUT)
    public ResponseEntity<Concept> updateConceptTopic(@PathVariable int id, @PathVariable String topic) {
        /*if (!conceptService.findoptOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);*/
        Concept concept = conceptService.findOne(id);
        concept.setTopic(topicService.findOne(topic).get());
        conceptService.save(concept);
        return new ResponseEntity<>(concept,HttpStatus.OK);
    }
    //endregion
}
