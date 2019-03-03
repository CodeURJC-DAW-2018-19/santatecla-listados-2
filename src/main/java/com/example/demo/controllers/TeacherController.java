package com.example.demo.controllers;

import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerService;
import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.item.Item;
import com.example.demo.item.ItemService;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionService;
import com.example.demo.topic.Topic;
import com.example.demo.topic.TopicService;
import com.example.demo.uploadImages.Image;
import com.example.demo.uploadImages.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Controller
public class TeacherController {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private ConceptService conceptService;
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
        return "redirect: MainPage/Teacher/"+conceptName;
    }
}
