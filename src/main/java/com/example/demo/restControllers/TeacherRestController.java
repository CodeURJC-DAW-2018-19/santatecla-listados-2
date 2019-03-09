package com.example.demo.restControllers;

import com.example.demo.answer.AnswerService;
import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.item.Item;
import com.example.demo.item.ItemService;
import com.example.demo.question.Question;
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

    interface ConceptDetails extends Concept.BasicInfo, Concept.ObjectLists, Topic.BasicInfo, Item.BasicInfo, Question.BasicInfo{}

    //Region Item

    @JsonView(ItemDetails.class)
    @GetMapping("/items/all")
    public ResponseEntity<List<Item>> getItems() {
        return new ResponseEntity<> (itemService.findAll(),HttpStatus.OK);
    }

    @JsonView(ItemDetails.class)
    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        if (itemService.findOne(id).isPresent())
            return new ResponseEntity<>(itemService.findOne(id).get(), HttpStatus.OK);
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/newItem", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Item newItem(@RequestBody Item item) {

        itemService.save(item);

        return item;
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/updateItem/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Item> updateItem(@PathVariable int id, @RequestBody Item updatedItem){

        Item item = itemService.findOne(id).get();


        if(item != null){
            updatedItem.setId(id);
            itemService.save(updatedItem);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);

        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/item/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Item> deleteItem(@PathVariable int id){
        if (!itemService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Item item = itemService.findOne(id).get();
        itemService.delete(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

        //New Item using URL parameters
    @JsonView(ItemDetails.class)
    @PostMapping("/item/{conceptName}/{text}/{checked}")
    public ResponseEntity<Item> newConcreteItem(@PathVariable String conceptName, @PathVariable String text, @PathVariable boolean checked ){
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
        return new ResponseEntity<>(item,HttpStatus.CREATED);
    }

        //Update Item using  URL parameters
    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/item/name/{id}/{newName}", method = RequestMethod.PUT)
    public ResponseEntity<Item> updateItemName(@PathVariable int id, @PathVariable String newName){
        if (!itemService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Item item = itemService.findOne(id).get();
        item.setName(newName);
        itemService.save(item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/item/check/{id}/{checked}", method = RequestMethod.PUT)
    public ResponseEntity<Item> updateItemChecked(@PathVariable int id, @PathVariable boolean checked){
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
    @GetMapping("/topics/all")
    public ResponseEntity<List<Topic>> getTopics() {
        return new ResponseEntity<>(topicService.findAll(),HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @GetMapping("/topic/{id}")
    public ResponseEntity<Topic> getTopic(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(topicService.findOne(id).get(),HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/newTopic", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Topic newTopic(@RequestBody Topic topic) {

        topicService.save(topic);

        return topic;
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/updateTopic/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Topic> updateTopic(@PathVariable int id, @RequestBody Topic updatedTopic){

        Topic topic = topicService.findOne(id).get();

        if(topic != null){
            updatedTopic.setId(id);
            topicService.save(updatedTopic);
            return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/topic/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Topic> deleteTopic(@PathVariable int id){
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic= topicService.findOne(id).get();
        topicService.delete(id);
        return new ResponseEntity<>(topic,HttpStatus.OK);
    }

        //New Topic using URL parameters
    @JsonView(TopicDetails.class)
    @PostMapping("/topic/{topicName}")
    public ResponseEntity<Topic> newConcreteTopic(@PathVariable String topicName){
        if (topicService.findOne(topicName).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = new Topic();
        topic.setName(topicName);
        topic.setHits(0);
        topic.setErrors(0);
        topic.setPendings(0);
        topic.setConcepts(new HashSet<>());
        topicService.save(topic);
        return new ResponseEntity<>(topic,HttpStatus.CREATED);
    }

        //Update Topic using  URL parameters
    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/topic/{id}/{newName}", method = RequestMethod.PUT)
    public ResponseEntity<Topic> updateTopicName(@PathVariable int id, @PathVariable String newName){
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topic.setName(newName);
        topicService.save(topic);
        return new ResponseEntity<>(topic,HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/topic/error/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Topic> updateErrorTopic(@PathVariable int id){
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topic.setErrors(topic.getErrors()+1);
        topicService.save(topic);
        return new ResponseEntity<>(topic,HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/topic/hit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Topic> updateHitTopic(@PathVariable int id){
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topic.setHits(topic.getHits()+1);
        topicService.save(topic);
        return new ResponseEntity<>(topic,HttpStatus.OK);
    }
    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/topic/pending/{id}", method = RequestMethod.PUT)
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
    @GetMapping("/concepts/all")
    public ResponseEntity<List<Concept>> getConcepts() {
        return new ResponseEntity<>(conceptService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/concept/{id}")
    public ResponseEntity<Concept> getConcept(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @RequestMapping(value = "/concept/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Concept> deleteConcept(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Concept concept =conceptService.findOne(id);
        conceptService.delete(concept);
        return new ResponseEntity<>(concept,HttpStatus.OK);
    }

            //New Concept using URL parameters
    @PostMapping("/concept/{name}/{topicname}")
    public ResponseEntity<Concept> newConcreteConcept(@PathVariable String name, @PathVariable String topicname) {
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

        //Update Concept using  URL parameters
    @RequestMapping(value = "/concept/{id}/newName/{name}", method = RequestMethod.PUT)
    public ResponseEntity<Concept> updateConceptName(@PathVariable int id, @PathVariable String name) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Concept concept = conceptService.findOne(id);
        concept.setName(name);
        conceptService.save(concept);
        return new ResponseEntity<>(concept,HttpStatus.OK);
    }

    @RequestMapping(value = "/concept/{id}/topic/{topic}", method = RequestMethod.PUT)
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
