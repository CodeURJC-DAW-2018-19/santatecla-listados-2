package com.example.demo.restControllers;

import com.example.demo.concept.Concept;
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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicRestController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private UserComponent userComponent;

    private final int DEFAULT_SIZE = 10;

    interface TopicDetails extends Topic.BasicInfo, Topic.BasicInfoGuest, Topic.ConceptList, Concept.BasicInfo, Concept.BasicInfoGuest {}
    interface TopicDetailsGuest extends Topic.BasicInfoGuest, Topic.ConceptList, Concept.BasicInfoGuest{}

    //Region Topic

    @GetMapping(value = "/")
    public MappingJacksonValue getTopics(@PageableDefault(size = DEFAULT_SIZE) Pageable page) {
        MappingJacksonValue topics = new MappingJacksonValue(this.topicService.findAll(page));
        if (userComponent.isLoggedUser()){
            topics.setSerializationView(TopicDetails.class);
        } else {
            topics.setSerializationView(TopicDetailsGuest.class);
        }
        return topics;
    }

    @JsonView(TopicDetails.class)
    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopic(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(topicService.findOne(id).get(), HttpStatus.OK);
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Topic> newTopic(@RequestBody Topic topic) {
        String name = topic.getName();
        if (topicService.findOne(name).isPresent())
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        topicService.save(topic);
        return new ResponseEntity<>(topic, HttpStatus.CREATED);
    }

    @JsonView(TopicDetails.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Topic> deleteTopic(@PathVariable int id) {
        if (!topicService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Topic topic = topicService.findOne(id).get();
        topicService.delete(id);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }
    //endregion
}

