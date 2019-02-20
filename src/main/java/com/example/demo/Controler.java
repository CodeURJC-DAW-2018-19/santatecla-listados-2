package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private static int numTopic; //en add de profesor debe de incrementarse
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

    @PostConstruct
    public void init() {
        Topic t1 = new Topic("Introducción",numTopic++);
        Topic t2 = new Topic("Árboles",numTopic++);
        Topic t3 = new Topic("Mapas",numTopic++);
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
        noMore = false;
        search = false;
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
    @GetMapping("/logOut")
    public String logOut(HttpSession session,Model model) {
        model.addAttribute("inOut", "out");
        model.addAttribute("LogIn", false);
        session.invalidate();
        return "redirect:/logIn";

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
        model.addAttribute("questions", q);
        model.addAttribute("LogIn", true);
        model.addAttribute("inOut", "out");
        model.addAttribute("urlLog","/logOut");
        return "TeacherConcept";
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
}