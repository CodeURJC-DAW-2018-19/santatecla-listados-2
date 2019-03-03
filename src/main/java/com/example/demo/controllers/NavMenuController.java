package com.example.demo.controllers;

import com.example.demo.conceptHeader.ConceptHeader;
import com.example.demo.conceptHeader.OpenTabs;
import com.example.demo.user.User;
import com.example.demo.user.UserComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class NavMenuController {
    @Autowired
    private OpenTabs openTabs;
    @Autowired
    private UserComponent userComponent;
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
}
