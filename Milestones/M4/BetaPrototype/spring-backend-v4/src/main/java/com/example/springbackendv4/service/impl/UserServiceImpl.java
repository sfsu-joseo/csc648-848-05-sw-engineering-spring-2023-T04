package com.example.springbackendv2.service.impl;

import com.example.springbackendv2.repository.UserRepository;
import com.example.springbackendv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer getStorageLeftByUserId(Long userId) {
        return userRepository.getStorageLeftByUserId(userId);
    }
}
