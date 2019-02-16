package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Controller
public class Controler {

    @Autowired
    private ConceptRepository conceptRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @PostConstruct
    public void init() {
        Topic t1=new Topic("Introducción");
        Topic t2=new Topic("Árboles");
        Topic t3=new Topic("Mapas");
        Concept c1=new Concept("Generics","StudentConcept.mustache");
        Concept c2=new Concept("Iteradores","StudentConcept.mustache");
        Concept c3=new Concept("JUNIT","StudentConcept.mustache");
        Concept c4=new Concept("Árboles LCRS","StudentConcept.mustache");
        Concept c5=new Concept("Árboles N-Arios","StudentConcept.mustache");
        Concept c6=new Concept("Árboles Binarios","StudentConcept.mustache");
        Concept c7=new Concept("Doble Hashing","StudentConcept.mustache");
        Concept c8=new Concept("Prueba Lineal","StudentConcept.mustache");
        Concept c9=new Concept("Prueba Cuadrátic","StudentConcept.mustache");
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

    }
    @GetMapping (path = "/MainPage")
    public String mainPage(Model model){

        List<Topic> topics=topicRepository.findAll();
        model.addAttribute("student", true);
        model.addAttribute("teacher", false);
        model.addAttribute("topics",topics);
        model.addAttribute("LogIn",true);
        model.addAttribute("inOut","out");
        List<Topic> topicsWithoutLastPosition=new ArrayList<>();
        if (topics.size()>1){
            topicsWithoutLastPosition=topics.subList(0,topics.size()-1);
        }
        Topic lastTopic=topics.get(topics.size()-1);
        model.addAttribute("Elements",topicsWithoutLastPosition);
        model.addAttribute("LastElement",lastTopic);
        return "MainPage";
    }

    @GetMapping (path = "/logIn")
    public String login(Model model) {
        model.addAttribute("inOut","out");
        model.addAttribute("LogIn",false);
        return "LogIn";
    }
    @GetMapping (path = "/teacher")
    public String teacher(Model model) {
        model.addAttribute("inOut","out");
        model.addAttribute("LogIn",true);
        /*Aqui hay que hacer un findall de todos los items de un determinado concepto y pasarselos como atributo al modelo y deberia funcionar la nueva pagina
        model.addAttribute("items",i);
           */
        return "TeacherConcept";
    }
}
