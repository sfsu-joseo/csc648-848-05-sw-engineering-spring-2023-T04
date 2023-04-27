package com.example.springbackendv2.controller;

import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.springbackendv2.config.StorageConfig;
import com.example.springbackendv2.exception.UserNotFoundException;
import com.example.springbackendv2.model.User;
import com.example.springbackendv2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin("http://localhost:3000/")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private StorageConfig s3;

    // --------------------- BASIC POST, GET, DELETE MAPPING BY UNIQUE ID
    // --------------------

    @PostMapping("/user")
    Long newUser(@RequestBody User newUser) {

        if (userRepository.checkUsername(newUser.getUser()) != null)
            return -1L;
        else if (userRepository.checkEmail(newUser.getEmail()) != null) {
            return -2L;
        }
        String rootFolder = newUser.getUser() + newUser.getId() + "/";
        userRepository.save(newUser);

        s3.s3Client().putObject(bucketName,rootFolder, new ByteArrayInputStream(new byte[0]), new ObjectMetadata());


        return newUser.getId();
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUser(newUser.getUser());
                    user.setFirstName(newUser.getFirstName());
                    user.setEmail(newUser.getEmail());

                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
        return "Deleted user with id: " + id;
    }

    // --------------------- MODIFIED MAPPING (refer to UserRepository for more
    // info) --------------------

    @GetMapping(value = "/login/{user}/{password}")
    public Long findByName(@PathVariable("user") String user, @PathVariable("password") String password) {
        User res = userRepository.findUsers(user);

        // USERNAME OR EMAIL DOES NOT EXIST
        if (res == null) {
            return -1L;
        }

        // PASSWORD DOES NOT MATCH
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
        User res = userRepository.authenticateUser(password, user_id);
        return res != null;
    }

    @GetMapping("/search/{query}")
    List<User> searchUser(@PathVariable String query) {
        /*
         * This is still a strict search. we will expand on this to
         * return a margin of close results
         */

        String newQuery = query.replace(" ", "");
        List<User> result = userRepository.searchUsersByUsername(newQuery);
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setPassword("");
            result.get(i).setEmail("");
        }
        if (result.isEmpty()) {
            return null;
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(result).getBody();
        }
    }

    @GetMapping(value = "/findFriends/{user_id}")
    List<User> findByUserId(@PathVariable("user_id") Long user_id) {
        List<User> res = userRepository.findFriends(user_id);
        for (int i = 0; i < res.size(); i++) {
            res.get(i).setPassword("");
            res.get(i).setEmail("");
        }
        return ResponseEntity.status(HttpStatus.OK).body(res).getBody();
    }

    @GetMapping(value = "/getPending/{user_id}")
    List<User> getPendingReq(@PathVariable("user_id") Long user_id) {
        List<User> res = userRepository.getPending(user_id);
        for (int i = 0; i < res.size(); i++) {
            res.get(i).setPassword("");
            res.get(i).setEmail("");
        }
        return ResponseEntity.status(HttpStatus.OK).body(res).getBody();
    }

    @GetMapping(value = "/mySavedUsers/{user_id}")
    List<User> getSavedUsers(@PathVariable("user_id") Long user_id) {
        List<User> res = userRepository.getSavedUsers(user_id);
        for (int i = 0; i < res.size(); i++) {
            res.get(i).setPassword("");
            res.get(i).setEmail("");
        }
        return ResponseEntity.status(HttpStatus.OK).body(res).getBody();
    }

    @GetMapping("/homepage/{userID}")
public String viewOtherUserHomepage(@PathVariable("user_id") int userID) {

    boolean isPrivate = userRepository.isPrivate(userID);

    // If the other user's account is private and the current user is not the owner,
    // return an error message
    if (isPrivate) {
        return "Access denied. This user's account is private.";
    }
    

    // If the other user's account is not private or the current user is the owner,
    // return the root folder of the other user's account as a string

    // root 'folder' in S3 should be replicated and returned.
    String rootFolder = userRepository.getUsername(userID) + userID + "/";

    return rootFolder;
}

}