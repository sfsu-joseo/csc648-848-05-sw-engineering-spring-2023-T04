package com.example.springbackendv2.controller;

import com.example.springbackendv2.exception.UserNotFoundException;
import com.example.springbackendv2.model.User;
import com.example.springbackendv2.repository.UserRepository;
import com.example.springbackendv2.service.UserService;
import com.example.springbackendv2.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* This class handles generic requests for user data,
* such as creating new user, finding existing user,
* and deleting a user. This will be adjusted later
* to reflect an updated user model. */

@RestController
@CrossOrigin("http://localhost:3000/")
public class UserController {
    /*
    * Notes:
    *
    * CrossOrigin -> permits requests from this frontend server.
    * PostMapping -> creates and inserts a new entity into database.
    * GetMapping -> gets an existing entity from database.
    * PutMapping -> updates params of an existing entity in database.
    * DeleteMapping -> deletes an entity by id from database.
    *
    * */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userService.createUser(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id){
        return  userRepository.findById(id)
                    .orElseThrow(()-> new UserNotFoundException(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("query") String query){
        return ResponseEntity.ok(userService.searchUsers(query));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return userRepository.findById(id)
                    .map(user -> {
                        user.setUsername(newUser.getUsername());
                        user.setName(newUser.getName());
                        user.setEmail(newUser.getEmail());

                        return userRepository.save(user);
                    }).orElseThrow(()-> new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
        return "Deleted user with id: " + id;
    }
}