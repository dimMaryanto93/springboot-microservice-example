package com.maryanto.dimas.example.controller;

import com.maryanto.dimas.example.dto.RegistrationDto;
import com.maryanto.dimas.example.dto.UserDto;
import com.maryanto.dimas.example.entity.Registration;
import com.maryanto.dimas.example.service.RegistrationService;
import com.maryanto.dimas.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final static Logger console = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationService registationService;

    @PostMapping("/new")
    public RegistrationDto getRegistration(
            @RequestHeader("Authorization") String token,
            @RequestBody RegistrationDto dto) {

        UserDto user = userService.getUser(token);
        Registration registration = new Registration(null, user.getId(), dto.getProjectName());
        registration = registationService.save(registration);

        return new RegistrationDto(registration.getId(), user, registration.getProjectName());
    }

}
