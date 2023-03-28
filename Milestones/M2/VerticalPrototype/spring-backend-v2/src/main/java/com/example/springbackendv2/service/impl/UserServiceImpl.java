package com.example.springbackendv2.service.impl;

import com.example.springbackendv2.model.User;
import com.example.springbackendv2.repository.UserRepository;
import com.example.springbackendv2.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> searchUsers(String query) {
        List<User> users = userRepository.searchRegisteredUsers(query);
        return users;
    }
    @Override
    public User createUser(User user){
        return userRepository.save(user);
    }
}
