package com.example.demo.restControllers;

import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptRepository;
import com.example.demo.uploadImages.Image;
import com.example.demo.uploadImages.ImageRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/image")
public class ImageRestController {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ConceptRepository conceptRepository;

    @GetMapping("/{id}")
    public void getImage(@PathVariable int id, HttpServletResponse response) throws IOException {
        Image image = imageRepository.findById(id).get();

        String imageDataBytes = image.getBase64().substring(image.getBase64().indexOf(",") + 1);
        InputStream stream = new ByteArrayInputStream(Base64.decodeBase64(imageDataBytes.getBytes()));
        response.addHeader("Content-Type", "image/jpeg");
        IOUtils.copy(stream, response.getOutputStream());
    }


    @PostMapping("/newImage/{title}/{concept}")
    public void postImage (@PathVariable String title,@PathVariable String concept, @RequestBody MultipartFile file,
                           HttpServletResponse response) throws IOException {
        Image i = new Image(title, "data:image/png;base64,"+ java.util.Base64.getEncoder().encodeToString(file.getBytes()));
        i.setConcept(conceptRepository.findByName(concept).get());
        conceptRepository.findByName(concept).get().setImage(i);

        imageRepository.save(i);

        String imageDataBytes = i.getBase64().substring(i.getBase64().indexOf(",")+1);
        InputStream stream = new ByteArrayInputStream(Base64.decodeBase64(imageDataBytes.getBytes()));
        response.addHeader("Content-Type","image/jpeg");
        IOUtils.copy(stream,response.getOutputStream());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Image> deleteImage(@PathVariable int id,HttpServletResponse response) throws IOException {
        Image image = imageRepository.findById(id).get();
        imageRepository.delete(image);
        return new ResponseEntity<>(image,HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}/updateImage/{name}", method = RequestMethod.PUT)
    public ResponseEntity<Image> updateConceptName(@PathVariable int id, @PathVariable String name,HttpServletResponse response ) throws IOException {
        if (!imageRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Image image = imageRepository.findById(id).get();
        image.setTitle(name);
        imageRepository.save(image);

        return new ResponseEntity<>(image,HttpStatus.OK);

    }


}