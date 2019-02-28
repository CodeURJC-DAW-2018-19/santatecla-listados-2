package com.example.demo.conceptHeader;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OpenTabs {
    private ArrayList<ConceptHeader> openTabs;

    public OpenTabs() {
        openTabs=new ArrayList<>();
    }

    public ArrayList<ConceptHeader> getOpenTabs() {
        return openTabs;
    }
    public void addTab(ConceptHeader c){
        openTabs.add(c);
    }
    public void deleteConceptHeader(ConceptHeader c){
        openTabs.remove(c);
    }
    public int size(){
        return openTabs.size();
    }

    public void setOpenTabs(ArrayList<ConceptHeader> array) {
        this.openTabs = array;
    }
    public boolean conceptContains(ConceptHeader c){
        return openTabs.contains(c);
    }
}
