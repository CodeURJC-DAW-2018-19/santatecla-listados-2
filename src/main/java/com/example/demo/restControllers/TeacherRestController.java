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
    public Item newItem(@PathVariable String conceptName,@PathVariable String text,@PathVariable boolean checked ){
        Item item = new Item();
        Concept concept = conceptService.findOne(conceptName);
        item.setName(text);
        item.setCorrect(checked);
        item.setConcept(concept);
        concept.setItem(item);
        conceptService.save(concept);
        return item;
    }

    @JsonView(ItemDetails.class)
    @GetMapping("/prueba/item/{id}")
    public Item getItem(@PathVariable int id) {
        return itemService.findOne(id).get();
    }

    @JsonView(ItemDetails.class)
    @GetMapping("/prueba/item/all")
    public List<Item> getItems() {
        return itemService.findAll();
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/prueba/item/{id}", method = RequestMethod.DELETE)
    public Item deleteItem(@PathVariable int id){
        Item item = itemService.findOne(id).get();
        itemService.delete(id);
        return item;
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/prueba/item/name/{id}/{newName}", method = RequestMethod.PUT)
    public Item updateItem(@PathVariable int id, @PathVariable String newName){
        Item item = itemService.findOne(id).get();
        item.setName(newName);
        itemService.save(item);
        return item;
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/prueba/item/check/{id}/{checked}", method = RequestMethod.PUT)
    public Item updateItem(@PathVariable int id, @PathVariable boolean checked){
        Item item = itemService.findOne(id).get();
        item.setCorrect(checked);
        itemService.save(item);
        return item;
    }
    //endregion

    //region TOPICS

    @JsonView(TopicDetails.class)
    @PostMapping("/prueba/topic/{topicName}")
    public Topic newTopic(@PathVariable String topicName){
        Topic topic = new Topic();
        topic.setName(topicName);
        topic.setHits(0);
        topic.setErrors(0);
        topic.setPendings(0);
        topic.setConcepts(new HashSet<>());
        topicService.save(topic);
        return topic;
    }

    @JsonView(TopicDetails.class)
    @GetMapping("/prueba/topic/{id}")
    public Topic getTopic(@PathVariable int id) {
        return topicService.findOne(id).get();
    }

    @JsonView(TopicDetails.class)
    @GetMapping("/prueba/topic/all")
    public List<Topic> getTopics() {
        return topicService.findAll();
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/prueba/topic/{id}", method = RequestMethod.DELETE)
    public Topic deleteTopic(@PathVariable int id){
        Topic topic= topicService.findOne(id).get();
        topicService.delete(id);
        return topic;
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/prueba/topic/{id}/{newName}", method = RequestMethod.PUT)
    public Topic updateTopic(@PathVariable int id, @PathVariable String newName){
        Topic topic = topicService.findOne(id).get();
        topic.setName(newName);
        topicService.save(topic);
        return topic;
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/prueba/topic/error/{id}", method = RequestMethod.PUT)
    public Topic updateErrorTopic(@PathVariable int id){
        Topic topic = topicService.findOne(id).get();
        topic.setErrors(topic.getErrors()+1);
        topicService.save(topic);
        return topic;
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/prueba/topic/hit/{id}", method = RequestMethod.PUT)
    public Topic updateHitTopic(@PathVariable int id){
        Topic topic = topicService.findOne(id).get();
        topic.setHits(topic.getHits()+1);
        topicService.save(topic);
        return topic;
    }
    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/prueba/topic/pending/{id}", method = RequestMethod.PUT)
    public Topic updatePendingTopic(@PathVariable int id){
        Topic topic = topicService.findOne(id).get();
        topic.setPendings(topic.getPendings()+1);
        topicService.save(topic);
        return topic;
    }
    //endregion

    //region CONCEPTS
    @JsonView(ConceptDetails.class)
    @GetMapping("/prueba/concepts/all")
    public List<Concept> getConcepts() {
        return conceptService.findAll();
    }
    //endregion

}
