package net.javaguides.springboot.service;

import net.javaguides.springboot.entity.User;

import java.util.List;

public interface UserService  {
    List<User> searchUsers(String query);

    User createUser(User user);
}