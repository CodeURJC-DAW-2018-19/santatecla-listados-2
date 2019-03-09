package com.example.demo.controllers;

import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.uploadImages.Image;
//import com.example.demo.UploadImages.ImageRepository;
import com.example.demo.uploadImages.ImageRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ImageController {

    @Autowired
    private ConceptService conceptService;
    @Autowired
    private ImageRepository imageRepository;


    @RequestMapping(value= "/{name}/image/upload", method = {RequestMethod.GET,RequestMethod.POST})
    public String handleFileUpload(Model model, @RequestParam String imageTitle,
                                   @RequestParam MultipartFile file, @PathVariable String name) throws IOException {
        Concept conceptName = conceptService.findOne(name).get();
        Image i = new Image(imageTitle, "data:image/png;base64,"+Base64.getEncoder().encodeToString(file.getBytes()));
        conceptName.setImage(i);
        i.setRelatedconcept(conceptName);
        imageRepository.save(i);
        return "redirect:/MainPage/Teacher/"+name;

    }






}