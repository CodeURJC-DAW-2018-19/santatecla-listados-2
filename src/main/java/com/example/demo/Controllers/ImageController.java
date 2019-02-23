package com.example.demo.Controllers;

import com.example.demo.Concept.ConceptRepository;
import com.example.demo.Concept.Concept;
import com.example.demo.UploadImages.Image;
import com.example.demo.User.User;
import com.example.demo.User.UserComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    private ConceptRepository conceptRepository;

    private static final Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");

    private AtomicInteger imageId = new AtomicInteger();
    private Map<Integer, Image> images = new ConcurrentHashMap<>();

    @Autowired
    private UserComponent userComponent;

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

        model.addAttribute("LogIn", true);
        model.addAttribute("inOut", "out");
        model.addAttribute("urlLog", "/logOut");
        model.addAttribute("images", images.values());

        return "images";
    }

    @RequestMapping("/image/Guest")
    public String imageGuest(Model model){

        model.addAttribute("student", false);
        model.addAttribute("teacher", false);
        model.addAttribute("guest", true);
        model.addAttribute("LogIn",true);
        model.addAttribute("inOut","in");
        model.addAttribute("urlLog","/logIn");

        model.addAttribute("images", images.values());

        return "images";
    }

    @RequestMapping("/newImage")
    public String newImage(Model model){
        User user = userComponent.getLoggedUser();

        if (user.getRol().equals("ROLE_TEACHER")){
            model.addAttribute("student", false);
            model.addAttribute("teacher", true);
            model.addAttribute("LogIn", true);
            model.addAttribute("inOut", "out");
            model.addAttribute("urlLog", "/logOut");
        }

        return "index";
    }

    @RequestMapping(value = "/image/upload", method = RequestMethod.POST)
    public String handleFileUpload(Model model, Authentication authentication, @RequestParam("imageTitle") String imageTitle,
                                   @RequestParam("file") MultipartFile file, @RequestParam("imageConcept") String imageConcept){

        User user = userComponent.getLoggedUser();
        int id = imageId.getAndIncrement();

        String fileName = "image-" + id + ".jpg";

        if (user.getRol().equals("ROLE_TEACHER")){
            model.addAttribute("student", false);
            model.addAttribute("teacher", true);
            model.addAttribute("LogIn", true);
            model.addAttribute("inOut", "out");
            model.addAttribute("urlLog", "/logOut");
        } else if (user.getRol().equals("ROLE_STUDENT")){
            model.addAttribute("student", true);
            model.addAttribute("teacher", false);
            model.addAttribute("LogIn", true);
            model.addAttribute("inOut", "out");
            model.addAttribute("urlLog", "/logOut");
        } else {
            model.addAttribute("guest", true);
            model.addAttribute("LogIn",true);
            model.addAttribute("inOut","in");
            model.addAttribute("urlLog","/logIn");
        }

        Concept conceptName =  conceptRepository.findByName(imageConcept);

        if (conceptName == null){
            model.addAttribute("error", "El concepto relacionado no existe");

            return "uploaded";

        } else if (!file.isEmpty()) {
            try {

                File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
                file.transferTo(uploadedFile);

                images.put(id, new Image(id, imageTitle, imageConcept));

                return "uploaded";

            } catch (Exception e) {

                model.addAttribute("error", e.getClass().getName() + ":" + e.getMessage());

                return "uploaded";
            }
        } else {

            model.addAttribute("error", "The file is empty");

            return "uploaded";
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

}