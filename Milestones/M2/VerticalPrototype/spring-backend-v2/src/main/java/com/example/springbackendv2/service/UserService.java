package com.example.springbackendv2.service;

import com.example.springbackendv2.model.User;

import java.util.List;

public interface UserService  {
    List<User> searchUsers(String query);

    User createUser(User user);
}