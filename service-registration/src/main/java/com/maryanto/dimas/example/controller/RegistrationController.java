package com.maryanto.dimas.example.controller;

import com.maryanto.dimas.example.model.Registration;
import com.maryanto.dimas.example.model.User;
import com.maryanto.dimas.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/general")
    public ResponseEntity<Registration> getRegistration(Principal principal) {
        return new ResponseEntity<>(
                new Registration(
                        1,
                        userService.getUser(principal.getName()),
                        Date.valueOf("2018-10-10")
                ), HttpStatus.CREATED);
    }

    @PostMapping("/createGeneral")
    public ResponseEntity<Registration> createRegistration(@RequestBody User user) {
        user = userService.createUser(user);
        return new ResponseEntity<>(
                new Registration(
                        null,
                        user,
                        Date.valueOf(LocalDate.now())
                ),
                HttpStatus.OK
        );
    }
}
