package com.example.demo.Controllers;

import com.example.demo.Concept.Concept;
import com.example.demo.Concept.ConceptService;
import com.example.demo.UploadImages.Image;
//import com.example.demo.UploadImages.ImageRepository;
import com.example.demo.User.User;
import com.example.demo.User.UserComponent;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ImageController {

    @Autowired
    private ConceptService conceptService;

    @Autowired
    private UserComponent userComponent;

    //@Autowired
    //private ImageRepository imageRepository;

    private static final Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");

    private AtomicInteger imageId = new AtomicInteger();
    private Map<Integer, Image> images = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() throws IOException {


        if (!Files.exists(FILES_FOLDER)) {
            Files.createDirectories(FILES_FOLDER);
        }


    }

    @RequestMapping("/image")
    public String index(Model model) {
        User user = userComponent.getLoggedUser();

        if (user.getRol().equals("ROLE_TEACHER")){
            model.addAttribute("student", false);
            model.addAttribute("teacher", true);
        }else {
            model.addAttribute("teacher", false);
            model.addAttribute("student", true);
        }

        model.addAttribute("logIn", true);
        model.addAttribute("inOut", "out");
        model.addAttribute("urlLog", "/logOut");
        model.addAttribute("images", images.values());

        return "Images";
    }

    @RequestMapping("/image/Guest")
    public String imageGuest(Model model){

        model.addAttribute("student", false);
        model.addAttribute("teacher", false);
        model.addAttribute("guest", true);
        model.addAttribute("logIn",true);
        model.addAttribute("inOut","in");
        model.addAttribute("urlLog","/logIn");

        model.addAttribute("images", images.values());

        return "Images";
    }

    @RequestMapping("/newImage")
    public String newImage(Model model){
        User user = userComponent.getLoggedUser();

        if (user.getRol().equals("ROLE_TEACHER")){
            model.addAttribute("student", false);
            model.addAttribute("teacher", true);
            model.addAttribute("logIn", true);
            model.addAttribute("inOut", "out");
            model.addAttribute("urlLog", "/logOut");
        }
        return "NewImage";
    }


    @RequestMapping(value = "/image/upload", method = RequestMethod.POST)
    public String handleFileUpload(Model model, @RequestParam("imageTitle") String imageTitle,
                                   @RequestParam("file") MultipartFile file, @RequestParam("imageConcept") String imageConcept){

        User user = userComponent.getLoggedUser();
        int id = imageId.getAndIncrement();

        String fileName = "image-" + id + ".jpg";

        if (user.getRol().equals("ROLE_TEACHER")){
            model.addAttribute("student", false);
            model.addAttribute("teacher", true);
            model.addAttribute("logIn", true);
            model.addAttribute("inOut", "out");
            model.addAttribute("urlLog", "/logOut");
        } else if (user.getRol().equals("ROLE_STUDENT")){
            model.addAttribute("student", true);
            model.addAttribute("teacher", false);
            model.addAttribute("logIn", true);
            model.addAttribute("inOut", "out");
            model.addAttribute("urlLog", "/logOut");
        } else {
            model.addAttribute("guest", true);
            model.addAttribute("logIn",true);
            model.addAttribute("inOut","in");
            model.addAttribute("urlLog","/logIn");
        }

        Concept conceptName =  conceptService.findOne(imageConcept);

        if (conceptName == null){
            model.addAttribute("error", "El concepto relacionado no existe");

            return "Uploaded";

        } else if (!file.isEmpty()) {
            try {

                File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
                file.transferTo(uploadedFile);

                images.put(id, new Image(id, imageTitle, imageConcept));

                //Attempt to save images in our database
                //Image image = new Image(id, imageTitle, imageConcept);
                //imageRepository.save(image);
                //conceptName.setImage(image);
                //image.setRelatedconcept(conceptName);

                return "Uploaded";

            } catch (Exception e) {

                model.addAttribute("error", e.getClass().getName() + ":" + e.getMessage());

                return "uploaded";
            }
        } else {

            model.addAttribute("error", "The file is empty");

            return "Uploaded";
        }
    }

    @RequestMapping("/image/{id}")
    public void handleFileDownload(@PathVariable String id, HttpServletResponse res)
            throws FileNotFoundException, IOException {

        String fileName = "image-" + id + ".jpg";

        Path image = FILES_FOLDER.resolve(fileName);

        if (Files.exists(image)) {
            res.setContentType("image/jpeg");
            res.setContentLength((int) image.toFile().length());
            FileCopyUtils.copy(Files.newInputStream(image), res.getOutputStream());

        } else {
            res.sendError(404, "File" + fileName + "(" + image.toAbsolutePath() + ") does not exist");
        }
    }



    /* View concepts images method
    @GetMapping("/MainPage/Student/{name}/image")
    public String showConceptImages(Model model, @PathVariable String name){

        User user = userComponent.getLoggedUser();
        model.addAttribute("student", true);

        Concept wantedConcept =  conceptRepository.findByName(name);
        Integer identificador = wantedConcept.getId();

        if (identificador == null){
            model.addAttribute("error", true);
            model.addAttribute("mensajeError", "este concepto no tiene imagenes");
            model.addAttribute("conceptImage", false);
            return"ConceptImages";
        }else {
            model.addAttribute("conceptImage", true);
            model.addAttribute("error", false);
            model.addAttribute("id", i);
            return"ConceptImages";
        }
    }*/

}