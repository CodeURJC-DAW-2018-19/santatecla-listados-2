package com.example.demo.Controllers;
import com.example.demo.conceptHeader.ConceptHeader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class LogOutController {
    private ArrayList<ConceptHeader> array = new ArrayList<>();
    @GetMapping("/logOut")
    public String logOut(HttpSession session, Model model) {
        model.addAttribute("inOut", "out");
        model.addAttribute("logIn", false);
        session.invalidate();
        return "redirect:/logIn";
    }
    void addConceptHeader(ConceptHeader c){
        array.add(c);
    }
    void deleteConceptHeader(ConceptHeader c){
        array.remove(c);
    }
    public int size(){
        return array.size();
    }

    public ArrayList<ConceptHeader> getArray() {
        return array;
    }

    public void setArray(ArrayList<ConceptHeader> array) {
        this.array = array;
    }
    public void empty(){
        array = new ArrayList<>();
    }
    boolean conceptContains(ConceptHeader c){
        return array.contains(c);
    }

}
