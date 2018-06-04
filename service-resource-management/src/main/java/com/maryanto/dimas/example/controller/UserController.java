package com.maryanto.dimas.example.controller;

import com.maryanto.dimas.example.model.User;
import com.maryanto.dimas.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final static Logger console = LoggerFactory.getLogger(UserController.class);

    /**
     * don't use for rest template because the principal is NullPointerException
     * @param principal
     * @return
     */
    @GetMapping("/me")
    public ResponseEntity<User> findByEmail(Principal principal) {
        console.info("user logged in : {}", principal.getName());
        User user = userRepository.findByEmail(principal.getName());
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured("hasRole('ADMIN')")
    @GetMapping("/byEmail")
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        console.info("params: {}", email);
        User user = userRepository.findByEmail(email);
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/created")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user = userRepository.save(user);
        if (user.getId() != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") User user) {
        user = userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") User user) {
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
