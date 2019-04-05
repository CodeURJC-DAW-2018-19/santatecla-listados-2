package com.example.demo.topic;

import com.example.demo.diagram.DiagramInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    @Autowired
    private TopicRepository repository;

    public Optional<Topic> findOne(int id) {
        return repository.findById(id);
    }

    public Optional<Topic> findOne(String name) {
        return repository.findByName(name);
    }
    public List<Topic> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    public Page<Topic> findAll(Pageable page) {
        return repository.findAll(page);
    }


    public List<Topic> findAll() {
        return repository.findAll();
    }

    public void save(Topic topic) {
        repository.save(topic);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
    public void delete(Topic t) {
        repository.delete(t);
    }
    public int getSize(){ return repository.findAll().size();}


    //new methods to build the diagram in angular

    public List<DiagramInfo> generateDiagramInfo(){
        List<DiagramInfo> diagramInfo = new ArrayList<>();
        for (Topic t: this.findAll()){
            diagramInfo.add(new DiagramInfo(t.getName(), t.getErrors(), t.getHits(), t.getPendings()));
        }
        return diagramInfo;
    }

    public Page<DiagramInfo> generateDiagramInfoPage(Pageable page){
        return new PageImpl<>(generateDiagramInfo());
    }

}
