package com.manav.JotDown.service;

import com.manav.JotDown.entity.User;
import com.manav.JotDown.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User inputUser) {
        userRepository.save(inputUser);
    }

    public boolean saveNewUser(User inputUser) {
        try {
            inputUser.setPassword(passwordEncoder.encode(inputUser.getPassword()));
            inputUser.setRoles(Arrays.asList("USER"));
            userRepository.save(inputUser);
            return true;
        } catch (Exception exception) {
//            log.error("Error occured for {} : ", inputUser.getUserName(), exception);
            log.error("hahaha");
            log.warn("hahaha");
            log.info("hahaha");
            log.debug("hahaha");
            log.trace("hahaha");
            return false;
        }
    }

    public void saveNewAdminUser(User inputUser) {
        inputUser.setPassword(passwordEncoder.encode(inputUser.getPassword()));
        inputUser.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(inputUser);
    }

    public Optional<User> getUserById(ObjectId userId) {
        return userRepository.findById(userId);
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void deleteUserById(ObjectId userId) {
        userRepository.deleteById(userId);
    }

    public void deleteByUserName(String userName) {
        userRepository.deleteByUserName(userName);
    }
}
