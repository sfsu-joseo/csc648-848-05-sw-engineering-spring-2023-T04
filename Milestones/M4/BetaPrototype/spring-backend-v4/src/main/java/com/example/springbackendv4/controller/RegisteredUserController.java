package com.example.springbackendv4.controller;

import com.example.springbackendv4.exception.UserNotFoundException;
import com.example.springbackendv4.model.RegisteredUser;
import com.example.springbackendv4.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin("*")
public class RegisteredUserController {
    @Autowired
    private RegisteredUserRepository registeredUserRepository;

//    @Autowired
//    private UserServiceImpl userService;

    //   --------------------- BASIC POST, GET, DELETE MAPPING BY UNIQUE ID --------------------

    @PostMapping("/user")
    Long newUser(@RequestBody RegisteredUser newUser){

        if (registeredUserRepository.checkUsername(newUser.getUser()) != null)
            return -1L;
        else if (registeredUserRepository.checkEmail(newUser.getEmail()) != null) {
            return -2L;
        }
        String rootFolder = newUser.getUser() + newUser.getId() + "/";
        registeredUserRepository.save(newUser);

        return newUser.getId();
    }

    @GetMapping("/users")
    List<RegisteredUser> getAllUsers(){
        return registeredUserRepository.findAll();
    }

    @GetMapping("/user/{id}")
    RegisteredUser getUserById(@PathVariable Long id){
        return  registeredUserRepository.findById(id)
                    .orElseThrow(()-> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    RegisteredUser updateUser(@RequestBody RegisteredUser newRegisteredUser, @PathVariable Long id){
        return registeredUserRepository.findById(id)
                .map(registeredUser -> {
                    registeredUser.setUser(newRegisteredUser.getUser());
                    registeredUser.setFirstName(newRegisteredUser.getFirstName());
                    registeredUser.setEmail(newRegisteredUser.getEmail());

                    return registeredUserRepository.save(registeredUser);
                }).orElseThrow(()-> new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!registeredUserRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }

        registeredUserRepository.deleteById(id);
        return "Deleted user with id: " + id;
    }


    //   --------------------- MODIFIED MAPPING (refer to UserRepository for more info) --------------------

    @GetMapping(value = "/login/{user}/{password}")
    public Long findByName(@PathVariable("user") String user, @PathVariable("password") String password) {
        RegisteredUser res = registeredUserRepository.findUsers(user);

//        USERNAME OR EMAIL DOES NOT EXIST
        if(res == null) {
            return -1L;
        }

//        PASSWORD DOES NOT MATCH
        else if (!Objects.equals(res.getPassword(), password)) {
            System.out.println("this is being printed");
            return -2L;
        } else {
            return res.getId();
        }
    }

    @GetMapping(value = "/authenticate/{password}/{user_id}")
    public Boolean authenticateUser(@PathVariable("password") String password,
                                    @PathVariable("user_id") Long user_id) {
        RegisteredUser res = registeredUserRepository.authenticateUser(password, user_id);
        return res != null;
    }

    @GetMapping("/search/{query}")
    List<RegisteredUser> searchUser(@PathVariable String query)
    {
        /*   This is still a strict search. we will expand on this to
        return a margin of close results */

        String newQuery = query.replace(" ", "");
        List<RegisteredUser> result = registeredUserRepository.searchUsersByUsername(newQuery);
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setPassword("");
            result.get(i).setEmail("");
        }
        if(result.isEmpty()) {
            return null;
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(result).getBody();
        }
    }

    @GetMapping(value = "/findFriends/{user_id}")
    List<RegisteredUser> findByUserId(@PathVariable("user_id") Long user_id) {
        List<RegisteredUser> res = registeredUserRepository.findFriends(user_id);
        for (int i = 0; i < res.size(); i++) {
            res.get(i).setPassword("");
            res.get(i).setEmail("");
        } return ResponseEntity.status(HttpStatus.OK).body(res).getBody();
    }

    @GetMapping(value = "/getPending/{user_id}")
    List<RegisteredUser> getPendingReq(@PathVariable("user_id") Long user_id) {
        List<RegisteredUser> res = registeredUserRepository.getPending(user_id);
        for (int i = 0; i < res.size(); i++) {
            res.get(i).setPassword("");
            res.get(i).setEmail("");
        } return ResponseEntity.status(HttpStatus.OK).body(res).getBody();
    }

    @GetMapping(value = "/mySavedUsers/{user_id}")
    List<RegisteredUser> getSavedUsers(@PathVariable("user_id") Long user_id) {
        List<RegisteredUser> res = registeredUserRepository.getSavedUsers(user_id);
        for (int i = 0; i < res.size(); i++) {
            res.get(i).setPassword("");
            res.get(i).setEmail("");
        } return ResponseEntity.status(HttpStatus.OK).body(res).getBody();
    }


    // ------------------------------------ CURRENTLY NOT USED ------------------------------------------- //


    //    @GetMapping("/shareLink")
//    public String generateLink(String username){
//        String token = registeredUserRepository.findToken(username);
//        String link = "http://www.studentsources.com/sources/" + token;
//        return link;
//    }

    //    @PostMapping("/user/signup")
//    String signup(@RequestBody RegisteredUser newRegisteredUser){
//        return userService.encryptPasswordAndCreateUser(newRegisteredUser);
//    }

//    @PostMapping("/user/login")
//    String login(@RequestBody RegisteredUser registeredUser){
//        return userService.authorizeUser(registeredUser.getUser(), registeredUser.getPassword());
//    }
}