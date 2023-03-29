package com.example.springbackendv2.service;

import com.example.springbackendv2.model.User;

import java.util.List;

public interface UserService  {

    User createUser(User user);

    String encryptPasswordAndCreateUser(User newUser);

    String authorizeUser(String username, String password);
}