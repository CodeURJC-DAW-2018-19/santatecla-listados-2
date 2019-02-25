package com.example.demo.Controllers;

import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerService;
import com.example.demo.Concept.Concept;
import com.example.demo.Concept.ConceptService;
import com.example.demo.conceptHeader.ConceptHeader;
import com.example.demo.Item.Item;
import com.example.demo.Item.ItemService;
import com.example.demo.Question.Question;
import com.example.demo.Question.QuestionService;
import com.example.demo.Topic.Topic;
import com.example.demo.Topic.TopicService;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@Controller
public class MainController {
    private static int i = 0;
    private static boolean search;
    private static boolean noMore;
    private static boolean noMoreQues, noMoreQuesNC;
    private static String text;
    private boolean noMoreItems;
    private static LogOutController logOutController = new LogOutController();
    private boolean noMoreQuestionsNC;
    @Autowired
    private UserComponent userComponent;
    @Autowired
    private ConceptService conceptService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
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
        model.addAttribute("moreThanOne",topics.size()>1);
        return "MainPage";
    }

    @GetMapping("/MainPage/Student/{name}")
    public String concept(Model model, @PathVariable String name) {
        Concept concept = conceptService.findOne(name);
        if (concept == null)
            return null;
        Set<Question> q = concept.getQuestions();
        model.addAttribute("name", name);
        model.addAttribute("questions", q);
        model.addAttribute("logIn", true);
        model.addAttribute("inOut", "out");
        model.addAttribute("urlLog", "/logOut");
        model.addAttribute("topics",concept);
        model.addAttribute("moreThanOne",false);
        return "StudentConcept";
    }
    @GetMapping("/MainPage/Teacher/{name}")
    public String teacherConcept(Model model, @PathVariable String name) {
        Concept concept = conceptService.findOne(name);
        if (concept == null)
            return null;
        Set<Question> q = concept.getQuestions();
        List<Item> i = itemService.findByConceptName(name);
        model.addAttribute("name", name);
        model.addAttribute("items", i);
        model.addAttribute("topics", i);
        model.addAttribute("questions", q);
        model.addAttribute("logIn", true);
        model.addAttribute("inOut", "out");
        model.addAttribute("urlLog","/logOut");


        return "TeacherConcept";
    }
    @RequestMapping(value = "/MainPage/search", method =  RequestMethod.POST)
    public String search(Model model, @RequestParam String searchText) {
        noMore = false;
        noMoreQues = false;
        noMoreQuesNC = false;
        noMoreItems = false;
        search = false;
        text = searchText;
        model.addAttribute("numero",i);
        model.addAttribute("student", true);
        model.addAttribute("teacher", false);
        List<Topic> topics = topicService.findAll();
        model.addAttribute("topics",topics);
        model.addAttribute("logIn",true);
        model.addAttribute("inOut","out");
        model.addAttribute("Elements",topics);
        model.addAttribute("urlLog","/logOut");
        model.addAttribute("moreThanOne",topics.size()>1);
        search = true;
        return "MainPage";
    }

    @RequestMapping(value = "/MainPage/addTo{name}", method = RequestMethod.POST)
    public String addConcept(Model model, HttpServletResponse res, @RequestParam String conceptName, @PathVariable String name) throws IOException {
        Optional<Topic> t = topicService.findOne(name);
        Concept c = new Concept(conceptName);
        if (t.isPresent()){
            c.setTopic(t.get());
            t.get().setConcept(c);
            conceptService.save(c);
            return "redirect:/MainPage";
        }else{
            res.sendError(404, "Topic with this concept asociated to does not exist");
            return "/error";

        }

    }

    @RequestMapping(value = "/MainPage/addTopic", method = RequestMethod.POST)
    public String addTopic(Model model, @RequestParam String topicName) {
        Topic t = new Topic(topicName, topicService.findAll().size() + 1);
        topicService.save(t);
        return "redirect:/MainPage";
    }

    @RequestMapping(value = "/MainPage/deleteConcept{name}", method = RequestMethod.POST)
    public String deleteConcept(Model model, @PathVariable String name) {
        Concept c = conceptService.findOne(name);
        c.getTopic().removeConcept(c);
        conceptService.delete(c);
        return "redirect:/MainPage";
    }

    @RequestMapping(value = "/MainPage/deleteTopic{name}", method = RequestMethod.POST)
    public String deleteTopic(Model model, @PathVariable String name,HttpServletResponse res)throws IOException {
        Optional<Topic> t = topicService.findOne(name);
        if (t.isPresent()){
            t.get().removeConcepts();
            topicService.delete(t.get());
            return "redirect:/MainPage";
        }else{
            res.sendError(404, "Topic to delete does not exist");
            return "/error";
        }

    }

    @RequestMapping(value = "/MainPage/deleteItem", method = RequestMethod.POST)
    public String deleteItem(Model model, @RequestParam String itemName,HttpServletResponse res) throws IOException {
        Optional<Item> i = itemService.findOne(itemName);
        if (i.isPresent()){
            itemService.delete(i.get());
            return "redirect:/MainPage/Teacher/" + i.get().getConcept().getName();
        }else{
            res.sendError(404, "Item to delete does not exist");
            return "/error";
        }

    }


    @GetMapping(path = "/MoreQuestions/{name}")
    public String moreQuestionButton(Model model, @PageableDefault(size = 10) Pageable pageable, @PathVariable String name) {
        Concept concept = conceptService.findOne(name);
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
        Concept concept = conceptService.findOne(name);
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

    @GetMapping (path = "/TopicMoreButton")
    public String topicMoreButton(Model model, @PageableDefault(size = 10) Pageable pageable){
        i++;
        model.addAttribute("number",i);
        Page<Topic> topics;
        if (!search || text.equals("")) {
            search = false;
            topics = topicService.findAll(pageable);
        }else{
            List<Concept> concepts=conceptService.findByNameContaining(text);
            List<Topic> topicList = topicService.findByNameContaining(text);
            List<Topic> t=new ArrayList<>();
            for (Concept c:concepts) {
                if (!t.contains(c.getTopic()))
                    t.add(c.getTopic());
            }
            for (Topic topic: topicList){
                if (!t.contains(topic))
                    t.add(topic);
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
        if (u == null){
            model.addAttribute("student", false);
            model.addAttribute("teacher", false);
            model.addAttribute("guest", true);
            model.addAttribute("topics",topics);
            model.addAttribute("inOut","in");
            model.addAttribute("urlLog","/logIn");
        }else if (u.getRol().equals("ROLE_TEACHER")){
            model.addAttribute("student", false);
            model.addAttribute("teacher", true);
            model.addAttribute("guest", false);
            model.addAttribute("inOut","out");
            model.addAttribute("urlLog","/logOut");
        }else if (u.getRol().equals("ROLE_STUDENT")){
            model.addAttribute("student", true);
            model.addAttribute("teacher", false);
            model.addAttribute("guest", false);
            model.addAttribute("inOut","out");
            model.addAttribute("urlLog","/logOut");
        }
        model.addAttribute("topics",topics);
        model.addAttribute("logIn",true);
        if (topics.isEmpty() && !noMore){
            noMore=true;
            model.addAttribute("noMore",true);
        }
        return "TopicMore";
    }

    @GetMapping("/MainPage/DeleteHeaderConcept/{name}")
    public void deleteHeaderConcept(Model model,@PathVariable String name) {
        logOutController.deleteConceptHeader(new ConceptHeader(name));
        for (ConceptHeader c : logOutController.getArray()){
            System.out.println(c.getName());
        }
    }
    @GetMapping("/MainPage/HeaderConcept/{name}")
    public void addHeaderConcept(Model model,@PathVariable String name) {
        if (!logOutController.conceptContains(new ConceptHeader(name)) && logOutController.size()<11) {
            ConceptHeader conceptHeaderV = new ConceptHeader(name);
            logOutController.addConceptHeader(conceptHeaderV);
        }
    }
    @GetMapping("/MainPage/logOut")
    public String logout(Model model, HttpSession session) {
        logOutController.empty();
        return "redirect:/logOut";
    }
    @GetMapping("/MainPage/HeaderConcept")
    public String addHeaderConcept(Model model) {
        User u = userComponent.getLoggedUser();
        if (u == null){
            model.addAttribute("login",true);
            model.addAttribute("urlLog","/logIn");
            model.addAttribute("inOut","in");
        }else if (u.getRol().equals("ROLE_TEACHER")){
            model.addAttribute("student", false);
            model.addAttribute("teacher", true);
            model.addAttribute("login", false);
            model.addAttribute("inOut", "out");
            model.addAttribute("urlLog", "/MainPage/logOut");
        } else if (u.getRol().equals("ROLE_STUDENT")) {
            model.addAttribute("student", true);
            model.addAttribute("teacher", false);
            model.addAttribute("login",false);
            model.addAttribute("inOut","out");
            model.addAttribute("urlLog","/MainPage/logOut");
        }
        model.addAttribute("conceptHeader",logOutController.getArray());
        model.addAttribute("logIn",true);
        return "Header";
    }

    @GetMapping("/loadMoreItems/{name}")
    public String loadMoreItems(Model model, @PageableDefault(size = 10) Pageable pageable, @PathVariable String name) {
        Concept concept = conceptService.findOne(name);
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
        Concept concept = conceptService.findOne(name);
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

    @RequestMapping("logIn/newAccount")
    public String newAccount(Model model){
        model.addAttribute("show", true);
        model.addAttribute("showSubmit", true);
        return "NewAccount";
    }
    @GetMapping ("/MainPage/Teacher/{conceptName}/save/{text}/{checked}")
    public String addItem(Model model,@PathVariable String conceptName,@PathVariable String text, @PathVariable boolean checked){
        Optional<Item> o = itemService.findOne(text);
        if (o.isPresent()){
            o.get().setCorrect(checked);
            this.itemService.save(o.get());
        }else{
            Item i=new Item(text,checked);
            Concept c=this.conceptService.findOne(conceptName);
            i.setConcept(c);
            this.itemService.save(i);
        }

        Item i=new Item(text,checked);
        Concept c=this.conceptService.findOne(conceptName);
        i.setConcept(c);
        this.itemService.save(i);
        return "redirect:/MainPage/Teacher/"+conceptName;
    }
    @GetMapping ("/MainPage/Teacher/{conceptName}/{answerName}/{mark}")
    public String correctPendingQuestion(Model model,@PathVariable String conceptName,@PathVariable String answerName, @PathVariable boolean mark){
        Concept c=conceptService.findOne(conceptName);
        Answer a=answerService.findOne(answerName);
        Question q = questionService.findOne(a.getQuestion().getQuestion());
        q.setCorrected(true);
        a.getQuestion().setCorrected(true);
        a.setMark(mark);
        c.setPendings(c.getPendings()-1);
        c.getTopic().setPendings(c.getTopic().getPendings()-1);
        if (mark){
            c.setHits(c.getHits()+1);
            c.getTopic().setHits(c.getTopic().getHits()+1);
        }else{
            c.setErrors(c.getErrors()+1);
            c.getTopic().setErrors(c.getTopic().getErrors()+1);
        }

        conceptService.save(c);
        answerService.save(a);
        questionService.save(q);
        return "redirect: MainPage/Teacher/"+conceptName;
    }
    @RequestMapping("/logIn/newAccount/try")
    public String newAccountTry(Model model, @RequestParam String username, @RequestParam String rol,
                                @RequestParam String name, @RequestParam String password) {

        if (username.equals("")) {

            model.addAttribute("show", true);
            model.addAttribute("emptyUserName", true);
            model.addAttribute("errorUserName", false);
            model.addAttribute("success", false);
            model.addAttribute("showSubmit", true);
            model.addAttribute("rolError", false);
            model.addAttribute("emptyName", false);
            model.addAttribute("emptyPassword", false);

            return "NewAccount";
        } else if(name.equals("")){

            model.addAttribute("show", true);
            model.addAttribute("emptyUserName", false);
            model.addAttribute("errorUserName", false);
            model.addAttribute("success", false);
            model.addAttribute("showSubmit", true);
            model.addAttribute("rolError", false);
            model.addAttribute("emptyName", true);
            model.addAttribute("emptyPassword", false);

            return "NewAccount";

        }else if(password.equals("")){
            model.addAttribute("show", true);
            model.addAttribute("emptyUserName", false);
            model.addAttribute("errorUserName", false);
            model.addAttribute("success", false);
            model.addAttribute("showSubmit", true);
            model.addAttribute("rolError", false);
            model.addAttribute("emptyName", false);
            model.addAttribute("emptyPassword", true);

            return "NewAccount";
        }else {

            User user = userRepository.findByUsername(username);
            if (user != null) {
                model.addAttribute("show", true);
                model.addAttribute("errorUserName", true);
                model.addAttribute("success", false);
                model.addAttribute("showSubmit", true);
                model.addAttribute("rolError", false);

                return "NewAccount";
            } else {
                model.addAttribute("errorUserName", false);


                if (rol.equals("TEACHER")) {
                    model.addAttribute("success", true);
                    model.addAttribute("show", false);
                    model.addAttribute("showSubmit", false);
                    model.addAttribute("rolError", false);
                    User newUser = new User(name, password, username, "ROLE_TEACHER");
                    userRepository.save(newUser);

                    return "NewAccount";
                } else if (rol.equals("STUDENT")) {
                    model.addAttribute("success", true);
                    model.addAttribute("show", false);
                    model.addAttribute("showSubmit", false);
                    model.addAttribute("rolError", false);
                    User newUser = new User(name, password, username, "ROLE_STUDENT");
                    userRepository.save(newUser);

                    return "NewAccount";
                } else {
                    model.addAttribute("show", true);
                    model.addAttribute("showSubmit", true);
                    model.addAttribute("rolError", true);
                    model.addAttribute("success", false);

                    return "NewAccount";
                }


            }
        }
    }
}