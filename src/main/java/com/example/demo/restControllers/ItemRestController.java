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
@RequestMapping("/api/items")
public class ItemRestController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ConceptService conceptService;

    private final int DEFAULT_SIZE = 10;

    interface ItemDetails extends Item.BasicInfo, Item.ItemConcept, Concept.BasicInfo, Concept.BasicInfoGuest {}


    //Region Item

    @JsonView(Item.BasicInfo.class)
    @GetMapping(value = "/")
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
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Item> newItem(@RequestBody Item item) {
        String name = item.getName();
        if (itemService.findOne(name).isPresent())
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        itemService.save(item);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @JsonView(ItemDetails.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Item> deleteItem(@PathVariable int id) {
        if (!itemService.findOne(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Item item = itemService.findOne(id).get();
        itemService.delete(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    //endregion
}


