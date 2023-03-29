package com.example.springbackendv2.service.impl;

import com.example.springbackendv2.exception.EmailAlreadyExistsException;
import com.example.springbackendv2.exception.InvalidCredentialsException;
import com.example.springbackendv2.exception.UserNotFoundException;
import com.example.springbackendv2.exception.UsernameAlreadyExistsException;
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
    public User createUser(User user){
        return userRepository.save(user);
    }

    @Override
    public String encryptPasswordAndCreateUser(User newUser) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {

        //Check if username is unique
        User userWithUsername = userRepository.findByUsername(newUser.getUsername());
        if(userWithUsername != null){
            throw new UsernameAlreadyExistsException(newUser.getUsername());
        }

        //Check if email is unique
        User userWithEmail = userRepository.findByEmail(newUser.getEmail());
        if(userWithEmail != null){
            throw new EmailAlreadyExistsException(newUser.getEmail());
        }

        String newPassword = encoder.encode(newUser.getPassword());
        newUser.setPassword(newPassword);
        User savedUser = userRepository.save(newUser);
        return savedUser.getId().toString();
    }

    @Override
    public String authorizeUser(String username, String password) throws UserNotFoundException, InvalidCredentialsException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UserNotFoundException(username);
        }
        if(user != null && encoder.matches(password, user.getPassword())) {
            return user.getId().toString(); // Returns true if password matches.
        } else {
            throw new InvalidCredentialsException();
        }
    }
}
