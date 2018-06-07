package com.maryanto.dimas.example.controller;

import com.maryanto.dimas.example.dto.RegistrationDto;
import com.maryanto.dimas.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final static Logger console = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/new")
    public RegistrationDto getRegistration(@RequestHeader("Authorization") String token) {
        return new RegistrationDto(1, userService.getUser(token), "Mandiri MITS");
    }

}
