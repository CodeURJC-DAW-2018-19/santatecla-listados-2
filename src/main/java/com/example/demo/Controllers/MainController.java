package com.example.demo.Controllers;

import com.example.demo.Answer.AnswerRepository;
import com.example.demo.Concept.Concept;
import com.example.demo.Concept.ConceptRepository;
import com.example.demo.Item.Item;
import com.example.demo.Item.ItemRepository;
import com.example.demo.Question.Question;
import com.example.demo.Question.QuestionRepository;
import com.example.demo.Topic.Topic;
import com.example.demo.Topic.TopicRepository;
import com.example.demo.User.User;
import com.example.demo.User.UserComponent;
import com.example.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class MainController {
    private static int i = 0;
    private static boolean search;
    private static boolean noMore;
    private static String text;
    @Autowired
    private UserComponent userComponent;
    @Autowired
    private ConceptRepository conceptRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping(path = "/MainPage/Guest")
    public String mainPageVisitor(Model model) {
        List<Topic> topics=topicRepository.findAll();
        model.addAttribute("student", false);
        model.addAttribute("teacher", false);
        model.addAttribute("guest", true);
        model.addAttribute("topics",topics);
        model.addAttribute("LogIn",true);
        model.addAttribute("inOut","in");
        model.addAttribute("urlLog","/logIn");
        return "MainPage";
    }

    @GetMapping(path = "/MainPage")
    public String mainP(Model model) {
        List<Topic> topics=topicRepository.findAll();
        User u = userComponent.getLoggedUser();
        noMore = false;
        search = false;
        if (u.getRol().equals("ROLE_TEACHER")){
            model.addAttribute("student", false);
            model.addAttribute("teacher", true);
        }else if (u.getRol().equals("ROLE_STUDENT")){
            model.addAttribute("student", true);
            model.addAttribute("teacher", false);
        }
        model.addAttribute("topics",topics);
        model.addAttribute("LogIn",true);
        model.addAttribute("guest", false);
        model.addAttribute("inOut","out");
        model.addAttribute("urlLog","/logOut");
        return "/MainPage";
    }

    @GetMapping("/MainPage/Student/{name}")
    public String concept(Model model, @PathVariable String name) {
        Concept concept = conceptRepository.findByName(name);
        if (concept == null)
            return null;
        Set<Question> q = concept.getQuestions();
        model.addAttribute("questions", q);
        model.addAttribute("LogIn", true);
        model.addAttribute("inOut", "out");
        model.addAttribute("urlLog","/logOut");
        return "StudentConcept";
    }
    @GetMapping("/MainPage/Teacher/{name}")
    public String teacherConcept(Model model, @PathVariable String name) {
        Concept concept = conceptRepository.findByName(name);
        if (concept == null)
            return null;
        Set<Question> q = concept.getQuestions();
        List <Item> i = itemRepository.findByConceptName(name);
        model.addAttribute("items",i);
        model.addAttribute("questions", q);
        model.addAttribute("LogIn", true);
        model.addAttribute("inOut", "out");
        model.addAttribute("urlLog","/logOut");
        return "TeacherConcept";
    }
    @RequestMapping(value = "/MainPage/search", method =  RequestMethod.POST)
    public String search(Model model, @RequestParam String searchText) {
        noMore = false;
        search = false;
        text = searchText;
        model.addAttribute("numero",i);
        model.addAttribute("student", true);
        model.addAttribute("teacher", false);
        List<Topic> topics = topicRepository.findAll();
        model.addAttribute("topics",topics);
        model.addAttribute("LogIn",true);
        model.addAttribute("inOut","out");
        model.addAttribute("Elements",topics);
        model.addAttribute("urlLog","/logOut");
        search = true;
        return "MainPage";
    }
    @GetMapping (path = "/TopicMoreButton")
    public String topicMoreButton(Model model, @PageableDefault(size = 2) Pageable pageable){
        i++;
        model.addAttribute("numero",i);
        Page<Topic> topics;
        if (!search || text.equals("")) {
            search = false;
            topics = topicRepository.findAll(pageable);
        }else{
            List<Concept> concepts=conceptRepository.findByNameContainingOrTopicNameContaining(text,text);
            List<Topic> t=new ArrayList<>();
            for (Concept c:concepts) {
                if (!t.contains(c.getTopic()))
                    t.add(c.getTopic());

            }
            long start = pageable.getOffset();
            long end = (start + pageable.getPageSize()) > t.size() ? t.size() : (start + pageable.getPageSize());
            if (end<start){
                topics = new PageImpl<>(new ArrayList<>());
            }else {
                topics = new PageImpl<>(t.subList((int) start, (int) end), pageable, t.size());
            }
        }
        User u = userComponent.getLoggedUser();
        if (u.getRol().equals("ROLE_TEACHER")){
            model.addAttribute("student", false);
            model.addAttribute("teacher", true);
        }else if (u.getRol().equals("ROLE_STUDENT")){
            model.addAttribute("student", true);
            model.addAttribute("teacher", false);
        }
        model.addAttribute("topics",topics);
        model.addAttribute("LogIn",true);
        model.addAttribute("guest", false);
        model.addAttribute("inOut","out");
        model.addAttribute("urlLog","/logOut");
        if (topics.isEmpty() && !noMore){
            noMore=true;
            model.addAttribute("NoMore",true);
        }
        return "TopicMore";
    }
}