package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;


@Controller
public class Controler {

    @GetMapping (path = "/index")
    public String login(Model model) {
        ArrayList<Topic> temas=new ArrayList<>();
        ArrayList<Concept> con=new ArrayList<>();
        Topic t=new Topic("HOLA");
        Topic t1=new Topic("ADIOS");
        Topic t2=new Topic("q");
        Concept f=new Concept("HOLA","Index.mustache");
        Concept f2=new Concept("H","Index.mustache");
        Concept f3=new Concept("HO","Index.mustache");
        temas.add(t);
        temas.add(t1);
        temas.add(t2);
        con.add(f);
        con.add(f2);
        con.add(f3);

        model.addAttribute("student", false);
        model.addAttribute("concepts",con);
        model.addAttribute("topics",temas);
        return "MainPage";
    }
}
