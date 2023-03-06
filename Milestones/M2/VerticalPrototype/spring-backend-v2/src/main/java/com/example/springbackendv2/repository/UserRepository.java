package com.example.springbackendv2.repository;


import com.example.springbackendv2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
* This is a user repo for holding persistent data.
* This approach will probably make fetching data much
* quicker, as data can be loaded into memory from a
* database and fetched from memory. */

public interface UserRepository extends JpaRepository<User, Long> {
}