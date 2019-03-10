package com.example.demo.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping(value = "/error")
    public String error(Model model, HttpServletRequest req){
        Integer errorCode = (Integer)req.getAttribute("javax.servlet.error.status_code");
        Exception exception =(Exception) req.getAttribute("javax.servlet.error.exception");
       if (exception == null){
           if (errorCode == 403){
               model.addAttribute("exception", "Forbidden Access");
           }else {
               model.addAttribute("exception", "Unexpected error");
           }
       }else{
            model.addAttribute("exception",exception.getMessage());
       }
        model.addAttribute("errorCode",errorCode);
        return "Error";
    }

    @Override
    public String getErrorPath(){
        return "/error";
    }

}
