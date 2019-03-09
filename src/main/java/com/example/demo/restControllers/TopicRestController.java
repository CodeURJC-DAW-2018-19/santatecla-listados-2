package com.example.demo.restControllers;

import com.example.demo.concept.Concept;
import com.example.demo.topic.Topic;
import com.example.demo.topic.TopicService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicRestController {

    @Autowired
    private TopicService topicService;

    interface TopicDetails extends Topic.BasicInfo, Topic.ConceptList, Concept.BasicInfo {}

    //Region Topic

    @JsonView(TopicDetails.class)
    @GetMapping("/all")
    public ResponseEntity<List<Topic>> getTopics() {
        return new ResponseEntity<>(topicService.findAll(), HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopic(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(topicService.findOne(id).get(), HttpStatus.OK);
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
    public ResponseEntity<Topic> updateTopic(@PathVariable int id, @RequestBody Topic updatedTopic) {

        Topic topic = topicService.findOne(id).get();

        if (topic != null) {
            updatedTopic.setId(id);
            topicService.save(updatedTopic);
            return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Topic> deleteTopic(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topicService.delete(id);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    //New Topic using URL parameters
    @JsonView(TopicDetails.class)
    @PostMapping("/newTopic/{topicName}")
    public ResponseEntity<Topic> newConcreteTopic(@PathVariable String topicName) {
        if (topicService.findOne(topicName).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = new Topic();
        topic.setName(topicName);
        topic.setHits(0);
        topic.setErrors(0);
        topic.setPendings(0);
        topic.setConcepts(new HashSet<>());
        topicService.save(topic);
        return new ResponseEntity<>(topic, HttpStatus.CREATED);
    }

    //Update Topic using  URL parameters
    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/{id}/updateTopicName/{newName}", method = RequestMethod.PUT)
    public ResponseEntity<Topic> updateTopicName(@PathVariable int id, @PathVariable String newName) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topic.setName(newName);
        topicService.save(topic);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/error/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Topic> updateErrorTopic(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topic.setErrors(topic.getErrors() + 1);
        topicService.save(topic);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/hit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Topic> updateHitTopic(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topic.setHits(topic.getHits() + 1);
        topicService.save(topic);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/pending/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Topic> updatePendingTopic(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topic.setPendings(topic.getPendings() + 1);
        topicService.save(topic);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }
    //endregion
}

