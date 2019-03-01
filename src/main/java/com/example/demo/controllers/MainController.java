package com.example.demo.controllers;

import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerService;
import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.conceptHeader.ConceptHeader;
import com.example.demo.conceptHeader.OpenTabs;
import com.example.demo.item.Item;
import com.example.demo.item.ItemService;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionService;
import com.example.demo.topic.Topic;
import com.example.demo.topic.TopicService;
import com.example.demo.uploadImages.Image;
import com.example.demo.uploadImages.ImageRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserComponent;
import com.example.demo.user.UserRepository;
import org.apache.tomcat.util.codec.binary.StringUtils;
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
    @Autowired
    private OpenTabs openTabs;
    @Autowired
    private ImageRepository imageRepository;



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
        List<Image> imagesList = imageRepository.findByConcept(concept);
        Set<Question> q = concept.getQuestions();
        if (imagesList.isEmpty()){
            model.addAttribute("eImages",false);
        }else{
            model.addAttribute("eImages", true);
        }
        model.addAttribute("images",imagesList);
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
        Concept wantedConcept = conceptService.findOne(name);
        List<Image> imagesList = imageRepository.findByConcept(wantedConcept);
        Set<Question> q = concept.getQuestions();
        List<Item> i = itemService.findByConceptName(name);
        if (imagesList.isEmpty()){
            model.addAttribute("eImages",false);
        }else{
            model.addAttribute("eImages", true);
            int sizeL = imagesList.size();
            double th = (double) sizeL/3;
            int thInt = (int)Math.ceil(th);
            int height = thInt * 350;
            model.addAttribute("height", height + "px");
        }

        model.addAttribute("images",imagesList);
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
        openTabs.deleteConceptHeader(new ConceptHeader(name));
        for (ConceptHeader c : openTabs.getOpenTabs()){
            System.out.println(c.getName());
        }
    }
    @GetMapping("/MainPage/HeaderConcept/{name}")
    public void addHeaderConcept(Model model,@PathVariable String name) {
        if (!openTabs.conceptContains(new ConceptHeader(name)) && openTabs.size()<11) {
            ConceptHeader conceptHeaderV = new ConceptHeader(name);
            openTabs.addTab(conceptHeaderV);
        }
    }
    @GetMapping("/MainPage/logOut")
    public String logout(Model model, HttpSession session) {
        model.addAttribute("inOut", "out");
        model.addAttribute("logIn", false);
        session.invalidate();
        return "redirect:/logIn";
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
        model.addAttribute("conceptHeader",openTabs.getOpenTabs());
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

    @GetMapping (path = "/NewQuestion/{conceptName}")
    public String newQuestion(Model model, @PathVariable String conceptName){
        Concept concept = conceptService.findOne(conceptName);
        int typeItem = 0;
        List<Item> list = new ArrayList<>();
        int typeQuestion =(int)(Math.random() * 4);
        Question question;
        String questionName ="";
        String modalType = "";
        if (typeQuestion == 0){
            modalType ="modal0";
            model.addAttribute("modal0",true);
            model.addAttribute("modal1",false);
            model.addAttribute("modal2",false);
            model.addAttribute("modal3",false);
            questionName = "¿Cuáles son " + concept.getName() + " ?";
        }else if (typeQuestion == 1 ){
            modalType ="modal1";
            model.addAttribute("modal0",false);
            model.addAttribute("modal1",true);
            model.addAttribute("modal2",false);
            model.addAttribute("modal3",false);
            List<Item> itemsList = itemService.findByConceptName(conceptName);
            typeItem = (int)(Math.random()*itemsList.size()-1);
            questionName = "¿"+ itemsList.get(typeItem).getName() + " es un elemento de " + concept.getName()+ " ?";
        }else if (typeQuestion ==2) {
            modalType ="modal2";
            model.addAttribute("modal0",false);
            model.addAttribute("modal1",false);
            model.addAttribute("modal2",true);
            model.addAttribute("modal3",false);
            List<Item> itemsList = itemService.findCorrect(true);
            typeItem = (int) (Math.random() * itemsList.size() - 1);
            itemsList.remove(typeItem);
            String otherItems = "";
            for (Item i : itemsList) {
                otherItems += i.getName() + ", ";
            }
            questionName = "¿Qué elemento falta en " + otherItems + " para completar la lista de " + concept.getName() + " ?";

        }else if (typeQuestion == 3){
            modalType ="modal3";
            model.addAttribute("modal0",false);
            model.addAttribute("modal1",false);
            model.addAttribute("modal2",false);
            model.addAttribute("modal3",true);
            List<Item> itemsList = itemService.findByConceptName(conceptName);
            for (Item i: itemsList){
                System.out.println(i.getName());
            }
            int itemsN = (int) Math.floor(Math.random() * (3)+2);
            String string = "";
            for (int i = 0; i<2; i++){
                typeItem = (int)(Math.random()*itemsList.size()-1);
                list.add(itemsList.get(typeItem));
                string += itemsList.get(typeItem).getName()+ ", ";
                itemsList.remove(typeItem);
            }
            questionName = "¿Qué elementos de " + string + " no son parte de " + concept.getName() + " ?";
        }

        if (typeQuestion ==0. || typeQuestion == 2){
            question = new Question(questionName, "" +typeQuestion, false );
        }else{
            question = new Question(questionName, "" +typeQuestion, true );
        }
        model.addAttribute(modalType, true);
        model.addAttribute("questions", list);
        model.addAttribute("typeQuestion", question);
        concept.setQuestion(question);
        question.setConcept(concept);
        questionService.save(question);
        conceptService.save(concept);
        return "NewQuestions";
    }
    @GetMapping(path = "/setAnswer/{id}")
    public String addAnswer(Model model, Answer answer, @PathVariable int id) {
        System.out.println("He entrado por la pregunta 0 o 2");
        Question question1 = questionService.findOne(id);
        answer.setQuestion(question1);
        answer.setMark(false);
        answer.setAnswerTest("Respuesta");
        question1.setCorrected(false);
        question1.setAnswer(answer);
        Concept c = question1.getConcept();
        Topic topic = c.getTopic();
        c.setPendings(c.getPendings()+1);
        topic.setPendings(topic.getPendings()+1);
//        conceptService.save(c);
        questionService.save(question1);
        return "redirect:/MainPage/Student/"+question1.getConcept().getName();
    }

    @GetMapping(path = "/sendAnswer/{question}/{correct}")
    public String sendAnswer(Model model, @PathVariable int question, @PathVariable boolean correct){
        Question question1 = questionService.findOne(question);
        Answer answer = new Answer();
        Concept c = question1.getConcept();
        Topic topic = c.getTopic();
        Set<Item> itemSet = question1.getConcept().getItems();
        Item selected = new Item();
        for (Item item:
             itemSet) {
            if (question1.getQuestion().contains(item.getName())){
                selected = item;
            }
        }
        if (selected.isCorrect() == correct ){
            answer.setMark(true);
            answer.setOpenAnswer("Verdadero");
            c.setHits(c.getHits()+1);
            topic.setHits(topic.getHits()+1);
        }
        else {
            answer.setMark(false);
            answer.setOpenAnswer("Falso");
            c.setErrors(c.getErrors()+1);
            topic.setErrors(topic.getErrors()+1);
        }
        answer.setAnswerTest("Respuesta");
        answer.setQuestion(question1);
        question1.setAnswer(answer);
        questionService.save(question1);
        return "redirect:/MainPage/Student/"+questionService.findOne(question).getConcept().getName();
    }

    @GetMapping(path = "/optedItems/{id}/{ret}/{total}")
    public String optedItems(Model model, @PathVariable int  id, @PathVariable String ret, @PathVariable String total) {
        System.out.println("He entrado por la pregunta 3");
        String[] items = ret.split("plus");
        String[] all = total.split("plus    ");
        ArrayList<String> items1= new ArrayList<>(Arrays.asList(items));
        ArrayList<String> all1= new ArrayList<>(Arrays.asList(all));
        StringBuilder showed = new StringBuilder(" ");
        for (String string: items) {
            showed.append(string).append(", ");
        }
        Answer answer = new Answer();

        Question question = questionService.findOne(id);
        Concept c = question.getConcept();
        Topic topic = c.getTopic();
        int result = 0;
        int result1 = 0;

        for (String s: items1) {
            for (Item i: question.getConcept().getItems()) {
                if(i.getName().equals(s) && i.isCorrect()){
                    result++;
                }
            }
        }
        for (String s: all1) {
            for (Item i: question.getConcept().getItems()) {
                if(i.getName().equals(s) && i.isCorrect()){
                    result1++;
                }
            }
        }

        if(result==result1){
            answer.setMark(true);
            c.setHits(c.getHits()+1);
            topic.setHits(topic.getHits()+1);
        }else {
            answer.setMark(false);
            c.setErrors(c.getErrors() + 1);
            topic.setErrors(topic.getErrors() + 1);
        }
        answer.setOpenAnswer(showed.toString());
        answer.setQuestion(question);
        answer.setAnswerTest("Respuesta");
        question.setAnswer(answer);


     //   conceptService.save(c);
        questionService.save(question);

        return "redirect:/MainPage/Student/"+questionService.findOne(id).getConcept().getName();
    }
}