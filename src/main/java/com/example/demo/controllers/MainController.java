package com.example.demo.controllers;

import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.item.Item;
import com.example.demo.item.ItemService;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionService;
import com.example.demo.topic.Topic;
import com.example.demo.topic.TopicService;
import com.example.demo.user.User;
import com.example.demo.user.UserComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@Controller
public class MainController {
    private static int i = 0;
    private static boolean search;
    private static boolean noMore;
    private static boolean noMoreQues, noMoreQuesNC;
    private boolean noMoreItems;
    private boolean noMoreQuestionsNC;
    private String text;
    @Autowired
    private UserComponent userComponent;
    @Autowired
    private ConceptService conceptService;
    @Autowired
    private TopicService topicService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ItemService itemService;


    @GetMapping(path = "/MainPage/Guest")
    public String mainPageVisitor(Model model) {
        noMore = false;
        noMoreQues = false;
        noMoreItems = false;
        noMoreQuesNC = false;
        search = false;
        List<Topic> topics = topicService.findAll();
        model.addAttribute("student", false);
        model.addAttribute("teacher", false);
        model.addAttribute("guest", true);
        model.addAttribute("topics", topics);
        model.addAttribute("logIn", true);
        model.addAttribute("inOut", "in");
        model.addAttribute("urlLog", "/logIn");
        return "MainPage";
    }

    @GetMapping(path = "/MainPage")
    public String mainP(Model model) {
        List<Topic> topics = topicService.findAll();
        User u = userComponent.getLoggedUser();
        noMore = false;
        noMoreQues = false;
        noMoreQuesNC = false;
        noMoreItems = false;
        search = false;
        if (u.getRol().equals("ROLE_TEACHER")) {
            model.addAttribute("student", false);
            model.addAttribute("teacher", true);
        } else if (u.getRol().equals("ROLE_STUDENT")) {
            model.addAttribute("student", true);
            model.addAttribute("teacher", false);
        }
        model.addAttribute("topics", topics);
        model.addAttribute("logIn", true);
        model.addAttribute("guest", false);
        model.addAttribute("inOut", "out");
        model.addAttribute("urlLog", "/logOut");
        model.addAttribute("moreThanOne", topics.size() > 1);
        return "MainPage";
    }


    @GetMapping(path = "/MoreQuestions/{name}")
    public String moreQuestionButton(Model model, @PageableDefault(size = 10) Pageable pageable, @PathVariable String name) {
        Concept concept = conceptService.findOne(name).get();
        if (concept == null)
            return null;
        List<Question> q = questionService.findByConceptAndCorrected(concept, true);
        Page<Question> questions;
        long start = pageable.getOffset();
        long end = (start + pageable.getPageSize()) > q.size() ? q.size() : (start + pageable.getPageSize());
        if (end < start) {
            questions = new PageImpl<>(new ArrayList<>());
        } else {
            questions = new PageImpl<>(q.subList((int) start, (int) end), pageable, q.size());
        }
        model.addAttribute("questions", questions);
        model.addAttribute("logIn", true);
        model.addAttribute("inOut", "out");
        model.addAttribute("urlLog", "/logOut");
        boolean noMore2 = false;
        if (questions.isEmpty() && !noMoreQues) {
            noMore2 = true;
            noMoreQues = true;
        }
        model.addAttribute("noMore", noMore2);
        return "MoreQuestions";
    }

    @GetMapping(path = "/MoreQuestionsNo/{name}")
    public String moreQuestionButtonNo(Model model, @PageableDefault(size = 10) Pageable pageable, @PathVariable String name) {
        Concept concept = conceptService.findOne(name).get();
        if (concept == null)
            return null;
        List<Question> q = questionService.findByConceptAndCorrected(concept, false);
        Page<Question> questions;
        long start = pageable.getOffset();
        long end = (start + pageable.getPageSize()) > q.size() ? q.size() : (start + pageable.getPageSize());
        if (end < start) {
            questions = new PageImpl<>(new ArrayList<>());
        } else {
            questions = new PageImpl<>(q.subList((int) start, (int) end), pageable, q.size());
        }
        model.addAttribute("questions", questions);
        model.addAttribute("logIn", true);
        model.addAttribute("inOut", "out");
        model.addAttribute("urlLog", "/logOut");
        boolean noMore1 = false;
        if (questions.isEmpty() && !noMoreQuesNC) {
            noMore1 = true;
            noMoreQuesNC = true;
        }
        model.addAttribute("noMore", noMore1);
        return "NoCorrectQuestions";
    }

    @GetMapping(path = "/TopicMoreButton")
    public String topicMoreButton(Model model, @PageableDefault(size = 10) Pageable pageable) {
        i++;
        model.addAttribute("number", i);
        Page<Topic> topics;
        if (!search || text.equals("")) {
            search = false;
            topics = topicService.findAll(pageable);
        } else {
            List<Concept> concepts = conceptService.findByNameContaining(text);
            List<Topic> topicList = topicService.findByNameContaining(text);
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
                topics = new PageImpl<>(new ArrayList<>());
            } else {
                topics = new PageImpl<>(t.subList((int) start, (int) end), pageable, t.size());
            }
        }
        User u = userComponent.getLoggedUser();
        if (u == null) {
            model.addAttribute("student", false);
            model.addAttribute("teacher", false);
            model.addAttribute("guest", true);
            model.addAttribute("topics", topics);
            model.addAttribute("inOut", "in");
            model.addAttribute("urlLog", "/logIn");
        } else if (u.getRol().equals("ROLE_TEACHER")) {
            model.addAttribute("student", false);
            model.addAttribute("teacher", true);
            model.addAttribute("guest", false);
            model.addAttribute("inOut", "out");
            model.addAttribute("urlLog", "/logOut");
        } else if (u.getRol().equals("ROLE_STUDENT")) {
            model.addAttribute("student", true);
            model.addAttribute("teacher", false);
            model.addAttribute("guest", false);
            model.addAttribute("inOut", "out");
            model.addAttribute("urlLog", "/logOut");
        }
        model.addAttribute("topics", topics);
        model.addAttribute("logIn", true);
        if (topics.isEmpty() && !noMore) {
            noMore = true;
            model.addAttribute("noMore", true);
        }
        return "TopicMore";
    }


    @GetMapping("/loadMoreItems/{name}")
    public String loadMoreItems(Model model, @PageableDefault(size = 10) Pageable pageable, @PathVariable String name) {
        Concept concept = conceptService.findOne(name).get();
        if (concept == null)
            return null;
        List<Item> i = itemService.findByConceptName(name);
        Page topics;
        long start = pageable.getOffset();
        long end = (start + pageable.getPageSize()) > i.size() ? i.size() : (start + pageable.getPageSize());
        if (end < start) {
            topics = new PageImpl<>(new ArrayList<>());
        } else {
            topics = new PageImpl<>(i.subList((int) start, (int) end), pageable, i.size());
        }
        model.addAttribute("items", topics);
        if (topics.isEmpty() && !noMoreItems) {
            noMoreItems = true;
            model.addAttribute("noMore", true);
        }
        return "MoreItems";
    }

    @GetMapping("/loadMoreQuestionsNC/{name}")
    public String loadMoreQuestionsNC(Model model, @PageableDefault(size = 10) Pageable pageable, @PathVariable String name) {
        Concept concept = conceptService.findOne(name).get();
        if (concept == null)
            return null;
        List<Question> q = questionService.findByConceptAndCorrected(concept, false);
        Page<Question> questions;
        long start = pageable.getOffset();
        long end = (start + pageable.getPageSize()) > q.size() ? q.size() : (start + pageable.getPageSize());
        if (end < start) {
            questions = new PageImpl<>(new ArrayList<>());
        } else {
            questions = new PageImpl<>(q.subList((int) start, (int) end), pageable, q.size());
        }
        model.addAttribute("questions", questions);
        if (questions.isEmpty() && !noMoreQuestionsNC) {
            noMoreQuestionsNC = true;
            model.addAttribute("noMore", true);
        }
        return "MoreQuestionNC";
    }

    @RequestMapping(value = "/MainPage/search", method = RequestMethod.POST)
    public String search(Model model, @RequestParam String searchText) {
        noMore = false;
        noMoreQues = false;
        noMoreQuesNC = false;
        noMoreItems = false;
        search = false;
        text = searchText;
        model.addAttribute("numero", i);
        model.addAttribute("student", true);
        model.addAttribute("teacher", false);
        List<Topic> topics = topicService.findAll();
        model.addAttribute("topics", topics);
        model.addAttribute("logIn", true);
        model.addAttribute("inOut", "out");
        model.addAttribute("Elements", topics);
        model.addAttribute("urlLog", "/logOut");
        model.addAttribute("moreThanOne", topics.size() > 1);
        search = true;
        return "MainPage";
    }


}