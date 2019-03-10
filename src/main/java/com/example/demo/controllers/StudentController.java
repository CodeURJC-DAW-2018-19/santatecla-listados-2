package com.example.demo.controllers;

import com.example.demo.answer.Answer;
import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.item.Item;
import com.example.demo.item.ItemService;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionService;
import com.example.demo.topic.Topic;
import com.example.demo.uploadImages.Image;
import com.example.demo.uploadImages.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
public class StudentController {

    @Autowired
    private ConceptService conceptService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ItemService itemService;

    @GetMapping("/MainPage/Student/{name}")
    public String concept(Model model, @PathVariable String name) {
        Concept concept = conceptService.findOne(name).get();
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

    @GetMapping (path = "/NewQuestion/{conceptName}")
    public String newQuestion(Model model, @PathVariable String conceptName){
        Concept concept = conceptService.findOne(conceptName).get();
        int typeItem = 0;
        List<Item> list = new ArrayList<>();
        int typeQuestion =0;//(int)(Math.random() * 4);
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

    @GetMapping(path = "/optedItems/{id}/{ret}/{total}")
    public String optedItems(Model model, @PathVariable int  id, @PathVariable String ret, @PathVariable String total) {
        System.out.println("He entrado por la pregunta 3");
        String[] items = ret.split("plus");
        String[] all = total.split("plus");
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
        question.setAnswer(answer);
        questionService.save(question);

        return "redirect:/MainPage/Student/"+questionService.findOne(id).getConcept().getName();
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

}
