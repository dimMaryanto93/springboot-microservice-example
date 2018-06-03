package com.maryanto.dimas.example.controller;

import com.maryanto.dimas.example.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{email}")
    public ResponseEntity<User> getUser(
            @PathVariable("email") String email,
            @RequestParam String password) {
        return new ResponseEntity<>(
                new User(1, email, password),
                HttpStatus.CREATED
        );
    }
}
