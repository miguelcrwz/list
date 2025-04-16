package com.miguelcrwz.list.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;
    @PostMapping
    public ResponseEntity create(@RequestBody UserModel userModel) { 
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if(user != null) {
            System.out.println("User already exists.");
            return ResponseEntity.status(400).body("User already exists.");
        }
        var passWordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passWordHashred);
        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(201).body(userCreated);
    }
}
