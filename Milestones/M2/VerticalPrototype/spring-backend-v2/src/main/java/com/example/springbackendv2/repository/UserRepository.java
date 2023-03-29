package com.example.springbackendv2.repository;


import com.example.springbackendv2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* This is a user repo for holding persistent data.
* This approach will probably make fetching data much
* quicker, as data can be loaded into memory from a
* database and fetched from memory. */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user WHERE username LIKE :query OR name LIKE :query",nativeQuery = true)
    List<User> searchUsers(@Param("query") String query);


    User findByUsername(String username);

    User findByEmail(String email);


}