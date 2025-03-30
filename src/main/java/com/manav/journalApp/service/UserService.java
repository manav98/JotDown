package com.manav.journalApp.service;

import com.manav.journalApp.entity.User;
import com.manav.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveuser(User inputUser) {
        return userRepository.save(inputUser);
    }

    public Optional<User> getUserById(ObjectId userId) {
        return userRepository.findById(userId);
    }

    public User findByUserName(String userName) {
        return  userRepository.findByUserName(userName);
    }

    public void deleteUserById(ObjectId userId) {
        userRepository.deleteById(userId);
    }
}
