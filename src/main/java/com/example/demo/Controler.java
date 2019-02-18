package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
public class Controler {

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

    @GetMapping(path = "/MainPage")
    public String mainPage(Model model) {

        List<Topic> topics=topicRepository.findAll();
        model.addAttribute("student", true);
        model.addAttribute("teacher", false);
        model.addAttribute("topics",topics);
        model.addAttribute("LogIn",true);
        model.addAttribute("inOut","out");
        model.addAttribute("Elements",topics);
        return "MainPage";
    }

    @GetMapping(path = "/logIn")
    public String login(Model model) {
        model.addAttribute("inOut", "out");
        model.addAttribute("LogIn", false);
        return "LogIn";
    }

    @GetMapping(path = "/student")
    public String student(Model model) {
        List<Question> q = questionRepository.findAll();
        model.addAttribute("questions", q);
        model.addAttribute("LogIn", true);
        model.addAttribute("inOut", "out");
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
        return "TeacherConcept";
    }
    @RequestMapping("/MainPage/{name}")
    public String conceptPage(Model model, @PathVariable String name) {
        Concept concept = conceptRepository.findByName(name);
        if (concept == null)
            return null;
        Set<Question> q = concept.getQuestions();
        model.addAttribute("questions", q);
        model.addAttribute("LogIn", true);
        model.addAttribute("inOut", "out");
        return "StudentConcept";
    }
}