package com.example.springbackendv4.controller;

import com.example.springbackendv4.dto.GroupInfoDto;
import com.example.springbackendv4.model.ChatMembers;
import com.example.springbackendv4.model.GroupManager;
import com.example.springbackendv4.model.RegisteredUser;
import com.example.springbackendv4.repository.ChatMemberRepository;
import com.example.springbackendv4.repository.GroupManagerRepository;
import com.example.springbackendv4.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ChatMemberController {
    @Autowired
    ChatMemberRepository chatMemberRepository;

    @Autowired
    RegisteredUserRepository registeredUserRepository;

    @Autowired
    GroupManagerRepository groupManagerRepository;

    @GetMapping("/getGroups/{user_id}")
    List<GroupInfoDto> getAllCommentDtoByResourceId(@PathVariable("user_id") Long user_id){
        List<ChatMembers> list = chatMemberRepository.getAllChatGroupsByUserId(user_id);


        List<GroupInfoDto> adjustedList = new ArrayList<>();
        new RegisteredUser();
        RegisteredUser user;

        StringBuilder groupName = new StringBuilder();

//        populating data transfer object here
        for (ChatMembers chatMembers : list) {
            List<RegisteredUser> usersList = registeredUserRepository.getAllUsersByGroupId(chatMembers.getFk_group_id());
            GroupManager group = groupManagerRepository.getById(chatMembers.getFk_group_id());


            for (RegisteredUser users : usersList) {
                groupName.append(", ").append(users.getUser());
            }

            String adjustedGroupName = groupName.substring(2);
            GroupInfoDto groupInfoDto = new GroupInfoDto();
            groupInfoDto.setGroupName(String.valueOf(adjustedGroupName));
            groupInfoDto.setTotalMembers((long) usersList.size());
            groupInfoDto.setFk_owner_id(group.getFk_owner_id());
            groupInfoDto.setId(chatMembers.getFk_group_id());
            adjustedList.add(groupInfoDto);

            groupName = new StringBuilder();
        }
        return adjustedList;
    }

    @DeleteMapping("/leaveGroupChat/{group_id}/{user_id}")
    void leaveGroupChat(@PathVariable("group_id") Long group_id,
                        @PathVariable("user_id") Long user_id){
        chatMemberRepository.leaveGroupChat(group_id, user_id);
    }
}
