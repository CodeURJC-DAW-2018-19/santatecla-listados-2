package com.example.demo.controllers;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;
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
    @GetMapping("/MainPage/logOut")
    public String logout(Model model, HttpSession session) {
        model.addAttribute("inOut", "out");
        model.addAttribute("logIn", false);
        session.invalidate();
        return "redirect:/logIn";
    }
    @RequestMapping("logIn/newAccount")
    public String newAccount(Model model){
        model.addAttribute("show", true);
        model.addAttribute("showSubmit", true);
        return "NewAccount";
    }
    @RequestMapping("/logIn/newAccount/try")
    public String newAccountTry(Model model, @RequestParam String username, @RequestParam String rol,
                                @RequestParam String name, @RequestParam String password) {

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
