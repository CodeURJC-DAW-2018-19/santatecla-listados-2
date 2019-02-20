package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class Controler {
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

    @PostConstruct
    public void init() {
        Topic t1 = new Topic("Introducción");
        Topic t2 = new Topic("Árboles");
        Topic t3 = new Topic("Mapas");
        Concept c1 = new Concept("Generics", "StudentConcept.mustache");
        Concept c2 = new Concept("Iteradores", "StudentConcept.mustache");
        Concept c3 = new Concept("JUNIT", "StudentConcept.mustache");
        Concept c4 = new Concept("Árboles LCRS", "StudentConcept.mustache");
        Concept c5 = new Concept("Árboles N-Arios", "StudentConcept.mustache");
        Concept c6 = new Concept("Árboles Binarios", "StudentConcept.mustache");
        Concept c7 = new Concept("Doble Hashing", "StudentConcept.mustache");
        Concept c8 = new Concept("Prueba Lineal", "StudentConcept.mustache");
        Concept c9 = new Concept("Prueba Cuadrátic", "StudentConcept.mustache");
        Question q1 = new Question("¿Que es un tipo generico?", "PType0", false);
        Question q2 = new Question("¿Donde hay que usar los tipos genericos?", "PType3", true);
        Answer a1 = new Answer("Respuesta Abierta 0", false);
        Answer a2 = new Answer("Respuesta Cerrada 1", true);
        Item i1 = new Item("Patron Singleton",false);
        Item i2 = new Item ("No tengo muy claro que poner",true);
        User u1= new User("Alberto","123456789","Albertocalib_8","ROLE_STUDENT");
        User u2= new User("Alberto","123456789","a.canal.2016","ROLE_TEACHER");
        User u3= new User("Haritz","123456789","yo","ROLE_TEACHER");
        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);


        t1.setConcept(c1);
        t1.setConcept(c2);
        t1.setConcept(c3);
        c1.setTopic(t1);
        c2.setTopic(t1);
        c3.setTopic(t1);

        t2.setConcept(c4);
        t2.setConcept(c5);
        t2.setConcept(c6);
        c4.setTopic(t2);
        c5.setTopic(t2);
        c6.setTopic(t2);

        t3.setConcept(c7);
        t3.setConcept(c8);
        t3.setConcept(c9);
        c7.setTopic(t3);
        c8.setTopic(t3);
        c9.setTopic(t3);

        c1.setQuestion(q1);
        c1.setQuestion(q2);
        q1.setConcept(c1);
        q2.setConcept(c1);

        q1.setAnswer(a1);
        q2.setAnswer(a2);
        a1.setQuestion(q1);
        a2.setQuestion(q2);

        c1.setItem(i1);
        c1.setItem(i2);

        topicRepository.save(t1);
        topicRepository.save(t2);
        topicRepository.save(t3);
        conceptRepository.save(c1);
        conceptRepository.save(c2);
        conceptRepository.save(c3);
        conceptRepository.save(c4);
        conceptRepository.save(c5);
        conceptRepository.save(c6);
        conceptRepository.save(c7);
        conceptRepository.save(c8);
        conceptRepository.save(c9);
        questionRepository.save(q1);
        questionRepository.save(q2);
        answerRepository.save(a1);
        answerRepository.save(a2);


    }


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
        if (u.getRol().equals("ROLE_TEACHER")){
            model.addAttribute("student", false);
            model.addAttribute("teacher", true);
            model.addAttribute("guest", false);
            model.addAttribute("topics",topics);
            model.addAttribute("LogIn",true);
            model.addAttribute("inOut","out");
            model.addAttribute("urlLog","/logOut");
            return "/MainPage";
        }else if (u.getRol().equals("ROLE_STUDENT")){
            model.addAttribute("student", true);
            model.addAttribute("teacher", false);
            model.addAttribute("topics",topics);
            model.addAttribute("LogIn",true);
            model.addAttribute("guest", false);
            model.addAttribute("inOut","out");
            model.addAttribute("urlLog","/logOut");
            return "/MainPage";
        }
        return "loginerror";
    }

    @RequestMapping("/logIn")
    public String logIn(Model model) {
        model.addAttribute("inOut", "out");
        model.addAttribute("LogIn", false);
        return "LogIn";
    }
    @RequestMapping("/loginerror")
    public String logError(Model model) {
        model.addAttribute("inOut", "out");
        model.addAttribute("LogIn", false);
        return "loginerror";
    }
    @RequestMapping("/logOut")
    public String logOut(HttpSession session,Model model) {
        model.addAttribute("inOut", "out");
        model.addAttribute("LogIn", false);
        session.invalidate();
        return "/logIn";

    }

    @GetMapping(path = "/student")
    public String student(Model model) {
        List<Question> q = questionRepository.findAll();
        model.addAttribute("questions", q);
        model.addAttribute("LogIn", true);
        model.addAttribute("inOut", "out");
        model.addAttribute("urlLog","/logOut");
        return "StudentConcept";
    }

    @GetMapping(path = "/teacher")
    public String teacher(Model model) {
        model.addAttribute("inOut", "out");
        model.addAttribute("LogIn", true);
        List<Question> q = questionRepository.findAll();
        model.addAttribute("questions", q);
        List<Item> i = itemRepository.findAll();
        model.addAttribute("items",i);
        model.addAttribute("urlLog","/logOut");
        return "TeacherConcept";
    }
    @GetMapping("/MainPage/{name}")
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
        model.addAttribute("questions", q);
        model.addAttribute("LogIn", true);
        model.addAttribute("inOut", "out");
        model.addAttribute("urlLog","/logOut");
        return "TeacherConcept";
    }
    @RequestMapping(value = "/MainPage/search", method =  RequestMethod.POST)
    public String search(Model model, @RequestParam String searchText) {
        List<Concept> concepts=conceptRepository.findByNameContainingOrTopicNameContaining(searchText,searchText);
        HashSet<Topic> t=new HashSet<>();
        for (Concept c:concepts) {
            t.add(c.getTopic());
        }
        model.addAttribute("student", true);
        model.addAttribute("teacher", false);
        model.addAttribute("topics",t);
        model.addAttribute("LogIn",true);
        model.addAttribute("inOut","out");
        model.addAttribute("Elements",t);
        model.addAttribute("urlLog","/logOut");
        return "MainPage";
    }
}