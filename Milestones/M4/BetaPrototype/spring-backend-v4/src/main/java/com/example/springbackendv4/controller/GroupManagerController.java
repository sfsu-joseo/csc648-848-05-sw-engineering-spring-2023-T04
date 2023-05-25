package com.example.springbackendv4.controller;

import com.example.springbackendv4.exception.UserNotFoundException;
import com.example.springbackendv4.model.ChatMembers;
import com.example.springbackendv4.model.DirectMessages;
import com.example.springbackendv4.model.GroupManager;
import com.example.springbackendv4.model.RegisteredUser;
import com.example.springbackendv4.repository.ChatMemberRepository;
import com.example.springbackendv4.repository.DirectMessagesRepository;
import com.example.springbackendv4.repository.GroupManagerRepository;
import com.example.springbackendv4.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
public class GroupManagerController {

    @Autowired
    GroupManagerRepository groupManagerRepository;

    @Autowired
    RegisteredUserRepository registeredUserRepository;

    @Autowired
    DirectMessagesRepository directMessagesRepository;

    @Autowired
    ChatMemberRepository chatMemberRepository;

    @PostMapping("/createGroupChat")
    GroupManager createGroupChat(@RequestBody GroupManager groupManager, @RequestParam ArrayList<Long> user_ids){

        GroupManager groupRef = groupManagerRepository.save(groupManager);

        for (Long userId : user_ids) {
            ChatMembers chatMembers = new ChatMembers();
            chatMembers.setFk_group_id(groupRef.getId());
            chatMembers.setFk_member_id(userId);
            chatMemberRepository.save(chatMembers);
        }

        new RegisteredUser();
        RegisteredUser user;
        for (int i = 0; i < user_ids.size(); i++) {
            int finalI = i;
            user = registeredUserRepository.findById(user_ids.get(i))
                    .orElseThrow(()-> new UserNotFoundException(user_ids.get(finalI)));

            DirectMessages directMessages = new DirectMessages();
            directMessages.setFk_group_id(groupRef.getId());
            directMessages.setMessage("Added " + user.getUser() + " to group chat.");
            directMessages.setFk_user_id(user.getId());

            directMessagesRepository.save(directMessages);
        }

        return groupRef;
    }

    @DeleteMapping("/deleteGroup/{id}")
    String deleteGroup(@PathVariable Long id){

//        remove members of group
        chatMemberRepository.removeMembersByGroupId(id);

//        delete the chat thread
        directMessagesRepository.deleteMessagesByGroupId(id);

        if(!groupManagerRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }

        groupManagerRepository.deleteById(id);
        return "Deleted user with id: " + id;
    }
}
