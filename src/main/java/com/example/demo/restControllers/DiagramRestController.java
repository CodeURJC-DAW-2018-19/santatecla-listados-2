package com.example.demo.restControllers;


import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.diagram.DiagramInfo;
import com.example.demo.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class DiagramRestController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private ConceptService conceptService;

    @GetMapping("/diagramInfo")
    public Page<DiagramInfo> getDiagramInfo(@PageableDefault(size= 10) Pageable page){
        return topicService.generateDiagramInfoPage(page);
    }

    @GetMapping("/conceptDiagramInfo/{id}")
    public ResponseEntity<DiagramInfo> getConceptDiagramInfo(@PathVariable int id){
        if(!conceptService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Concept concept = conceptService.findOne(id).get();
        DiagramInfo diagramInfo = new DiagramInfo(concept.getName(), concept.getErrors(), concept.getHits(), concept.getPendings());
        return new ResponseEntity<>(diagramInfo, HttpStatus.OK);
    }

}
