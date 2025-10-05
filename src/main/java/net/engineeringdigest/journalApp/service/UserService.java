package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    public Optional<User> getUserById(ObjectId userId) {
        return userRepository.findById(userId);
    }

    public void deleteUserById(ObjectId userId) {
        userRepository.deleteById(userId);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
