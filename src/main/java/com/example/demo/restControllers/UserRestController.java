package com.example.demo.restControllers;

import com.example.demo.user.User;
import com.example.demo.user.UserComponent;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    UserComponent userComponent;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/logIn")
    public ResponseEntity<User> logIn(){
        if (userComponent.getLoggedUser() != null){
            return new ResponseEntity<>(userComponent.getLoggedUser(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/register/newUser", method = RequestMethod.POST)
    public ResponseEntity<User> register(@RequestBody User newUser){
        if(newUser == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User userCheckup = userRepository.findByUsername(newUser.getUsername());

        if(userCheckup != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setName(newUser.getName());
        user.setUsername(newUser.getUsername());
        user.setSurname(newUser.getSurname());
        user.setRol(newUser.getRol());
        user.setPassword(newUser.getPassword());
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{userName}")
    public ResponseEntity<User> getUser(@PathVariable String userName){
        User user = userRepository.findByUsername(userName);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
