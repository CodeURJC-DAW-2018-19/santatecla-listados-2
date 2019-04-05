package com.example.demo.restControllers;


import com.example.demo.diagram.DiagramInfo;
import com.example.demo.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class DiagramRestController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/diagramInfo")
    public Page<DiagramInfo> getDiagramInfo(@PageableDefault(size= 10) Pageable page){
        return topicService.generateDiagramInfoPage(page);
    }

}
