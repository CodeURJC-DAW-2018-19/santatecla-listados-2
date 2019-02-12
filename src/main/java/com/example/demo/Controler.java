package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Controler {

    @GetMapping (path = "/index")
    public String login(Model model) {
        return "Index";
    }
}
