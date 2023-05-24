package com.example.springbackendv4.controller;

import com.example.springbackendv4.exception.UserNotFoundException;
import com.example.springbackendv4.model.UserFriendRequests;
import com.example.springbackendv4.repository.FriendRequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserFriendRequestsController {
    @Autowired
    private FriendRequestsRepository friendRequestsRepository;


    //   --------------------- BASIC POST, GET, DELETE MAPPING BY UNIQUE ID --------------------


    @PostMapping("/request")
    UserFriendRequests requests(@RequestBody UserFriendRequests userFriendRequests){
        return friendRequestsRepository.save(userFriendRequests);
    }

    @GetMapping("/requests")
    List<UserFriendRequests> getAllRequests(){
        return friendRequestsRepository.findAll();
    }

    @GetMapping("/requests/{id}")
    UserFriendRequests getRequestsById(@PathVariable Long id){
        return  friendRequestsRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    @DeleteMapping("/requests/{id}")
    String deleteRequestById(@PathVariable Long id){
        if(!friendRequestsRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }

        friendRequestsRepository.deleteById(id);
        return "Deleted requests with id: " + id;
    }


    //   --------------------- MODIFIED MAPPING (refer to RequestRepository for more info) --------------------


    @GetMapping(value = "/getSent/{user_id}")
    List<UserFriendRequests> getSentReq(@PathVariable("user_id") Long user_id) {
        List<UserFriendRequests> res = friendRequestsRepository.getSent(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(res).getBody();
    }

    @DeleteMapping("/deleteRequest/{receiver_id}/{sender_id}")
    String deleteRequest(@PathVariable("receiver_id") Long receiver_id, @PathVariable("sender_id") Long sender_id){
        friendRequestsRepository.deleteRequest(receiver_id, sender_id);
        return "Deleted pending req from : " + receiver_id + ".Deleted by : " + sender_id;
    }
}
