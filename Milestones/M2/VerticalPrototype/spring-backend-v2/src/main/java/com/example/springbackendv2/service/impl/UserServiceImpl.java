package com.example.springbackendv2.service.impl;

import com.example.springbackendv2.model.User;
import com.example.springbackendv2.repository.UserRepository;
import com.example.springbackendv2.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);


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

    @Override
    public User encryptPasswordAndCreateUser(User newUser) {
        String newPassword = encoder.encode(newUser.getPassword());
        newUser.setPassword(newPassword);
        return userRepository.save(newUser);
    }

    @Override
    public Boolean authorizeUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            return encoder.matches(password, user.getPassword()); // Returns true if password matches.
        } else {
            return false;
        }
    }
}
