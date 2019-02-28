package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/logIn")
    public String logIn(Model model) {
        model.addAttribute("inOut", "out");
        model.addAttribute("logIn", false);
        model.addAttribute("errorPassword", false);
        return "LogIn";
    }
    @RequestMapping("/loginError")
    public String logError(Model model) {
        model.addAttribute("inOut", "out");
        model.addAttribute("logIn", false);
        model.addAttribute("errorPassword", true);
        return "LogIn";
    }
}
