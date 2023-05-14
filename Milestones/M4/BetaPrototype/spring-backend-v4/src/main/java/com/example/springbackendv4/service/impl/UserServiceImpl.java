package com.example.springbackendv4.service.impl;

import com.example.springbackendv4.exception.EmailAlreadyExistsException;
import com.example.springbackendv4.exception.InvalidCredentialsException;
import com.example.springbackendv4.exception.UserNotFoundException;
import com.example.springbackendv4.exception.UsernameAlreadyExistsException;
import com.example.springbackendv4.model.User;
import com.example.springbackendv4.repository.UserRepository;
import com.example.springbackendv4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer getStorageLeftByUserId(Long userId) {
        return userRepository.getStorageLeftByUserId(userId);
    }

    @Override
    public String encryptPasswordAndCreateUser(User newUser) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {

        //Check if username is unique
        User userWithUsername = userRepository.findByUsername(newUser.getUser());
        if(userWithUsername != null){
            throw new UsernameAlreadyExistsException(newUser.getUser());
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
