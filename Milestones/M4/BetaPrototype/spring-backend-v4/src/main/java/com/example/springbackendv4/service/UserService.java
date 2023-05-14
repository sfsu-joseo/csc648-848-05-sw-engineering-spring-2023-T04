package com.example.springbackendv4.service;

import org.springframework.data.repository.query.Param;

public interface UserService {

    Integer getStorageLeftByUserId(@Param("userId") Long userId);

    String encryptPasswordAndCreateUser(com.example.springbackendv4.model.User newUser);

    String authorizeUser(String username, String password);
}
