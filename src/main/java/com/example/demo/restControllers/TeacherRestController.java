package com.example.demo.restControllers;

import com.example.demo.answer.AnswerService;
import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.item.Item;
import com.example.demo.item.ItemService;
import com.example.demo.question.QuestionService;
import com.example.demo.topic.Topic;
import com.example.demo.topic.TopicService;
import com.example.demo.uploadImages.ImageRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/teacher")
public class TeacherRestController {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private ConceptService conceptService;

    interface ItemDetails extends Item.BasicInfo, Item.ItemConcept, Concept.BasicInfo{}

    interface TopicDetails extends Topic.BasicInfo, Topic.ConceptList, Concept.BasicInfo{}

    interface ConceptDetails extends Concept.BasicInfo, Concept.ObjectLists, Topic.BasicInfo,Item.BasicInfo{}

    //region item
    @JsonView(ItemDetails.class)
    @PostMapping("/prueba/item/{conceptName}/{text}/{checked}")
    public ResponseEntity<Item> newItem(@PathVariable String conceptName, @PathVariable String text, @PathVariable boolean checked ){
        Item item = new Item();
        Concept concept = conceptService.findOne(conceptName);
        for (Item i :
                concept.getItems()) {
            if(i.getName().equals(text)){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        item.setName(text);
        item.setCorrect(checked);
        item.setConcept(concept);
        concept.setItem(item);
        conceptService.save(concept);
        return new ResponseEntity<>(item,HttpStatus.OK);
    }

    @JsonView(ItemDetails.class)
    @GetMapping("/prueba/item/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        if (itemService.findOne(id).isPresent())
            return new ResponseEntity<>(itemService.findOne(id).get(), HttpStatus.OK);
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(ItemDetails.class)
    @GetMapping("/prueba/item/all")
    public ResponseEntity<List<Item>> getItems() {
        return new ResponseEntity<> (itemService.findAll(),HttpStatus.OK);
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/prueba/item/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Item> deleteItem(@PathVariable int id){
        if (!itemService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Item item = itemService.findOne(id).get();
        itemService.delete(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/prueba/item/name/{id}/{newName}", method = RequestMethod.PUT)
    public ResponseEntity<Item> updateItem(@PathVariable int id, @PathVariable String newName){
        if (!itemService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Item item = itemService.findOne(id).get();
        item.setName(newName);
        itemService.save(item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/prueba/item/check/{id}/{checked}", method = RequestMethod.PUT)
    public ResponseEntity<Item> updateItem(@PathVariable int id, @PathVariable boolean checked){
        if (!itemService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Item item = itemService.findOne(id).get();
        item.setCorrect(checked);
        itemService.save(item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    //endregion

    //region TOPICS

    @JsonView(TopicDetails.class)
    @PostMapping("/prueba/topic/{topicName}")
    public ResponseEntity<Topic> newTopic(@PathVariable String topicName){
        if (topicService.findOne(topicName).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = new Topic();
        topic.setName(topicName);
        topic.setHits(0);
        topic.setErrors(0);
        topic.setPendings(0);
        topic.setConcepts(new HashSet<>());
        topicService.save(topic);
        return new ResponseEntity<>(topic,HttpStatus.OK);

    }

    @JsonView(TopicDetails.class)
    @GetMapping("/prueba/topic/{id}")
    public ResponseEntity<Topic> getTopic(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(topicService.findOne(id).get(),HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @GetMapping("/prueba/topic/all")
    public ResponseEntity<List<Topic>> getTopics() {
        return new ResponseEntity<>(topicService.findAll(),HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/prueba/topic/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Topic> deleteTopic(@PathVariable int id){
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic= topicService.findOne(id).get();
        topicService.delete(id);
        return new ResponseEntity<>(topic,HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/prueba/topic/{id}/{newName}", method = RequestMethod.PUT)
    public ResponseEntity<Topic> updateTopic(@PathVariable int id, @PathVariable String newName){
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topic.setName(newName);
        topicService.save(topic);
        return new ResponseEntity<>(topic,HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/prueba/topic/error/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Topic> updateErrorTopic(@PathVariable int id){
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topic.setErrors(topic.getErrors()+1);
        topicService.save(topic);
        return new ResponseEntity<>(topic,HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/prueba/topic/hit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Topic> updateHitTopic(@PathVariable int id){
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topic.setHits(topic.getHits()+1);
        topicService.save(topic);
        return new ResponseEntity<>(topic,HttpStatus.OK);
    }
    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/prueba/topic/pending/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Topic> updatePendingTopic(@PathVariable int id){
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topic.setPendings(topic.getPendings()+1);
        topicService.save(topic);
        return new ResponseEntity<>(topic,HttpStatus.OK);
    }
    //endregion

    //region CONCEPTS
    @JsonView(ConceptDetails.class)
    @GetMapping("/prueba/concepts/all")
    public ResponseEntity<List<Concept>> getConcepts() {
        return new ResponseEntity<>(conceptService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/prueba/concepts/{id}")
    public ResponseEntity<Concept> getConcept(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(conceptService.findOne(id),HttpStatus.OK);
    }

    @PostMapping("/prueba/concepts/{name}/{topicname}")
    public ResponseEntity<Concept> postConcept(@PathVariable String name, @PathVariable String topicname) {
        if (topicService.findOne(topicname).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @RequestMapping(value = "/prueba/concepts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Concept> deleteConcept(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Concept concept =conceptService.findOne(id);
        conceptService.delete(concept);
        return new ResponseEntity<>(concept,HttpStatus.OK);
    }

    @RequestMapping(value = "/prueba/concepts/{id}/newName/{name}", method = RequestMethod.PUT)
    public ResponseEntity<Concept> updateConceptName(@PathVariable int id, @PathVariable String name) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Concept concept = conceptService.findOne(id);
        concept.setName(name);
        conceptService.save(concept);
        return new ResponseEntity<>(concept,HttpStatus.OK);
    }

    @RequestMapping(value = "/prueba/concepts/{id}/topic/{topic}", method = RequestMethod.PUT)
    public ResponseEntity<Concept> updateConceptTopic(@PathVariable int id, @PathVariable String topic) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Concept concept = conceptService.findOne(id);
        concept.setTopic(topicService.findOne(topic).get());
        conceptService.save(concept);
        return new ResponseEntity<>(concept,HttpStatus.OK);
    }
    //endregion
}
