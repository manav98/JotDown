package com.manav.JotDown.controller;

import com.manav.JotDown.entity.User;
import com.manav.JotDown.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> saveuser(@RequestBody User inputUser) {
        try {
            boolean isUserSaved = userService.saveNewUser(inputUser);
            if (isUserSaved) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
