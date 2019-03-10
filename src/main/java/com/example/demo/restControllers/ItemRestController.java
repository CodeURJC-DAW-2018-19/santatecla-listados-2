package com.example.demo.restControllers;

import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.item.Item;
import com.example.demo.item.ItemService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemRestController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ConceptService conceptService;

    private final int DEFAULT_SIZE = 10;

    interface ItemDetails extends Item.BasicInfo, Item.ItemConcept, Concept.BasicInfo {}


    //Region Item

    @JsonView(ItemDetails.class)
    @GetMapping("/all")
    public ResponseEntity<List<Item>> getItems() {
        return new ResponseEntity<>(itemService.findAll(), HttpStatus.OK);
    }

    @JsonView(Item.BasicInfo.class)
    @GetMapping(value = "/all/pag")
    public Page<Item> getTopics(@PageableDefault(size = DEFAULT_SIZE) Pageable page) {
        Page<Item> items = itemService.findAll(page);
        return items;
    }

    @JsonView(ItemDetails.class)
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        if (itemService.findOne(id).isPresent())
            return new ResponseEntity<>(itemService.findOne(id).get(), HttpStatus.OK);
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/newItem", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Item newItem(@RequestBody Item item) {

        itemService.save(item);

        return item;
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/updateItem/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Item> updateItem(@PathVariable int id, @RequestBody Item updatedItem) {

        if (itemService.findOne(id).isPresent()) {

            Item item = itemService.findOne(id).get();
            updatedItem.setId(id);
            itemService.save(updatedItem);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Item> deleteItem(@PathVariable int id) {
        if (!itemService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Item item = itemService.findOne(id).get();
        itemService.delete(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    //New Item using URL parameters
    @JsonView(ItemDetails.class)
    @PostMapping("/{conceptName}/{text}/{checked}")
    public ResponseEntity<Item> newConcreteItem(@PathVariable String conceptName, @PathVariable String text, @PathVariable boolean checked) {
        Item item = new Item();
        Concept concept = conceptService.findOne(conceptName).get();
        for (Item i :
                concept.getItems()) {
            if (i.getName().equals(text)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        item.setName(text);
        item.setCorrect(checked);
        item.setConcept(concept);
        concept.setItem(item);
        conceptService.save(concept);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    //Update Item using  URL parameters
    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/{id}/newName/{newName}", method = RequestMethod.PUT)
    public ResponseEntity<Item> updateItemName(@PathVariable int id, @PathVariable String newName) {
        if (!itemService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Item item = itemService.findOne(id).get();
        item.setName(newName);
        itemService.save(item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/{id}/checked/{checked}", method = RequestMethod.PUT)
    public ResponseEntity<Item> updateItemChecked(@PathVariable int id, @PathVariable boolean checked) {
        if (!itemService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Item item = itemService.findOne(id).get();
        item.setCorrect(checked);
        itemService.save(item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    //endregion
}


