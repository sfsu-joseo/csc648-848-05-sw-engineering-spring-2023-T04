package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
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
        List<User> users = userRepository.searchUsers(query);
        return users;
    }
    @Override
    public User createUser(User user){
        return UserRepository.save(user);
    }
}
