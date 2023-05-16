package com.example.springbackendv4.service;

import org.springframework.data.repository.query.Param;
import com.example.springbackendv4.model.User;

public interface UserService {

    Integer getStorageLeftByUserId(@Param("userId") Long userId);

    String encryptPasswordAndCreateUser(User newUser);

    String authorizeUser(String username, String password);

    void generateToken(User user);
}
