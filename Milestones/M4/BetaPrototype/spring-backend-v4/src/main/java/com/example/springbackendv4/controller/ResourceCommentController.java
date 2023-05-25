package com.example.springbackendv4.controller;

import com.example.springbackendv4.dto.MessengerDto;
import com.example.springbackendv4.exception.UserNotFoundException;
import com.example.springbackendv4.model.ResourceComment;
import com.example.springbackendv4.model.RegisteredUser;
import com.example.springbackendv4.repository.RegisteredUserRepository;
import com.example.springbackendv4.service.impl.ResourceCommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ResourceCommentController {

    @Autowired
    ResourceCommentServiceImpl resourceCommentService;

    @Autowired
    RegisteredUserRepository registeredUserRepository;

    @GetMapping("/getComments/{resource_id}")
    List<MessengerDto> getAllCommentDtoByResourceId(@PathVariable("resource_id") Long resourceId){
        List<ResourceComment> list = resourceCommentService.getAllCommentDtoByResourceId(resourceId);
        List<MessengerDto> adjustedList = new ArrayList<>();
        new RegisteredUser();
        RegisteredUser user;

//        populating data transfer object here
        for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            user = registeredUserRepository.findById(list.get(i).getFk_user_id())
                    .orElseThrow(()-> new UserNotFoundException(list.get(finalI).getFk_user_id()));

            MessengerDto messengerDto = new MessengerDto();
            messengerDto.setMessage(list.get(i).getComment());
            messengerDto.setDate_posted(list.get(i).getComment_date());
            messengerDto.setId(list.get(i).getId());
            messengerDto.setUser(user.getUser());

            adjustedList.add(messengerDto);
        }
        return adjustedList;
    }

    @GetMapping("/commentsByUser/{user_id}")
    List<ResourceComment> getAllCommentsByUserId(@PathVariable("user_id") Long userId){
        return resourceCommentService.getAllCommentByUserId(userId);
    }

    @PostMapping("/createComment")
    ResourceComment createComment(@RequestBody ResourceComment comment){
        return resourceCommentService.saveOrUpdateComment(comment);
    }

    @PostMapping("/saveComment")
    ResourceComment saveComment(@RequestBody ResourceComment comment){
        return resourceCommentService.saveOrUpdateComment(comment);
    }

    @PutMapping("/saveCommentText/{comment_id}")
    void saveCommentText(@RequestBody String text, @PathVariable("comment_id") Long commentId){
        resourceCommentService.updateCommentTextById(commentId, text);
    }

    @DeleteMapping("/deleteAllComments/{resource_id}")
    void deleteAllComments(@PathVariable("resource_id") Long resourceId){
        resourceCommentService.deleteByResourceId(resourceId);
    }

    @DeleteMapping("/deleteComment/{comment_id}")
    void deleteComment(@PathVariable("comment_id") Long commentId){
        resourceCommentService.deleteByCommentId(commentId);
    }
}
