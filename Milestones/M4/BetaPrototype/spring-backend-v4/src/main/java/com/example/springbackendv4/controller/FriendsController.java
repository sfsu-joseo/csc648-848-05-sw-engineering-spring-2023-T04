package com.example.springbackendv4.controller;

import com.example.springbackendv4.exception.UserNotFoundException;
import com.example.springbackendv4.model.Friends;
import com.example.springbackendv4.repository.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://54.215.216.18:3000/")
public class FriendsController {
    @Autowired
    private FriendsRepository friendsRepository;


    //   --------------------- BASIC POST, GET, DELETE MAPPING BY UNIQUE ID --------------------


    @PostMapping("/friend")
    Friends friends(@RequestBody Friends friends){
        return friendsRepository.save(friends);
    }

    @GetMapping("/friends")
    List<Friends> getAllFriends(){
        return friendsRepository.findAll();
    }

    @GetMapping("/friends/{id}")
    Friends getFriendsById(@PathVariable Long id){
        return  friendsRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    @DeleteMapping("/friends/{id}")
    String deleteFriendsById(@PathVariable Long id){
        if(!friendsRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }

        friendsRepository.deleteById(id);
        return "Deleted friends with id: " + id;
    }



    //   --------------------- MODIFIED MAPPING (refer to FriendRepository for more info) --------------------



    @DeleteMapping("/deleteFriends/{user_id}/{friend_id}")
    String deleteFriends(@PathVariable Long user_id, @PathVariable Long friend_id){
        friendsRepository.deleteFriends(user_id, friend_id);
        friendsRepository.deleteFriends(friend_id, user_id);
        return "Deleted friends : " + user_id + " and " + friend_id;
    }

}
