package com.example.springbackendv2.service;

import org.springframework.data.repository.query.Param;

public interface UserService {

    Integer getStorageLeftByUserId(@Param("userId") Long userId);
}
