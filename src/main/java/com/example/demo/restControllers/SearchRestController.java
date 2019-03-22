package com.example.demo.restControllers;

import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptRepository;
import com.example.demo.concept.ConceptService;
import com.example.demo.item.Item;
import com.example.demo.question.Question;
import com.example.demo.topic.Topic;
import com.example.demo.topic.TopicRepository;
import com.example.demo.topic.TopicService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping ("/api/search")
public class SearchRestController {
    @Autowired
    private ConceptService conceptService;
    @Autowired
    private TopicService topicService;

    interface TopicDetails extends Topic.BasicInfo, Topic.BasicInfoGuest, Topic.ConceptList, Concept.BasicInfo, Concept.BasicInfoGuest {}


    @JsonView(TopicDetails.class)
    @GetMapping("/")
    public ResponseEntity <Page<Topic>> getSearchResults(@RequestBody String searchString,@PageableDefault(size = 10) Pageable pageable){
        Page<Topic> topics;
        List<Concept> concepts = conceptService.findByNameContaining(searchString);
        List<Topic> topicList = topicService.findByNameContaining(searchString);
        List<Topic> t = new ArrayList<>();
        for (Concept c : concepts) {
            if (!t.contains(c.getTopic()))
                t.add(c.getTopic());
        }
        for (Topic topic : topicList) {
            if (!t.contains(topic))
                t.add(topic);
        }
        long start = pageable.getOffset();
        long end = (start + pageable.getPageSize()) > t.size() ? t.size() : (start + pageable.getPageSize());
        if (end < start) {
            topics= new PageImpl<>(new ArrayList<>());
        } else {
            topics = new PageImpl<>(t.subList((int) start, (int) end), pageable, t.size());
        }
        return new ResponseEntity<>(topics, HttpStatus.OK);
     }
}
