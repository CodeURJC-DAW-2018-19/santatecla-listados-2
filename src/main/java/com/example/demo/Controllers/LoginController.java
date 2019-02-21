package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @RequestMapping("/logIn")
    public String logIn(Model model) {
        model.addAttribute("inOut", "out");
        model.addAttribute("LogIn", false);
        model.addAttribute("errorPassword", false);
        return "LogIn";
    }
    @RequestMapping("/loginError")
    public String logError(Model model) {
        model.addAttribute("inOut", "out");
        model.addAttribute("LogIn", false);
        model.addAttribute("errorPassword", true);
        return "LogIn";
    }
    @GetMapping("/logOut")
    public String logOut(HttpSession session, Model model) {
        model.addAttribute("inOut", "out");
        model.addAttribute("LogIn", false);
        session.invalidate();
        return "redirect:/logIn";

    }
}
