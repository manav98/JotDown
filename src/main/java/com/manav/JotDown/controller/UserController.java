package com.manav.JotDown.controller;

import com.manav.JotDown.entity.User;
import com.manav.JotDown.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveuser(@RequestBody User inputUser) {
        try {
            return new ResponseEntity<>(userService.saveuser(inputUser), HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId userId) {
        Optional<User> fetchedUser = userService.getUserById(userId);
        if (fetchedUser.isPresent()) {
            return new ResponseEntity<>(fetchedUser.get(), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/id/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {
        User userInDb = userService.findByUserName(userName);
        if (userInDb != null) {
            userInDb.setPassword(user.getPassword());
            userService.saveuser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
