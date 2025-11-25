package com.manav.JotDown.controller;

import com.manav.JotDown.api.response.WeatherResponse;
import com.manav.JotDown.entity.User;
import com.manav.JotDown.service.UserService;
import com.manav.JotDown.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId userId) {
        Optional<User> fetchedUser = userService.getUserById(userId);
        if (fetchedUser.isPresent()) {
            return new ResponseEntity<>(fetchedUser.get(), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
//        ObjectId userId = userService.findByUserName(userName).getId();  //Delete by user ID
//        userService.deleteUserById(userId);
        userService.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.getUserByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("New Delhi");
        if (weatherResponse != null) {
            return new ResponseEntity<>("Hi " + authentication.getName() + " Weather temparature is: " + weatherResponse.getCurrent().temperature, HttpStatus.OK);
        }
        return new ResponseEntity<>("Hi " + authentication.getName(), HttpStatus.OK);
    }
}
