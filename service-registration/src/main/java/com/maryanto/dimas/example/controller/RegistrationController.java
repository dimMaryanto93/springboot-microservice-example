package com.maryanto.dimas.example.controller;

import com.maryanto.dimas.example.dto.RegistrationDto;
import com.maryanto.dimas.example.dto.UserDto;
import com.maryanto.dimas.example.entity.Registration;
import com.maryanto.dimas.example.service.RegistrationService;
import com.maryanto.dimas.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final static Logger console = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationService registationService;

    @GetMapping("/list")
    public List<RegistrationDto> list(Pageable page, @RequestHeader("Authorization") String token) {
        List<RegistrationDto> listDto = new ArrayList<>();
        Page<Registration> list = registationService.list(page);
        Iterator<Registration> iterator = list.iterator();
        while (iterator.hasNext()) {
            Registration registration = iterator.next();
            UserDto userDto = null;
            try {
                ResponseEntity<UserDto> responseEntity = userService.getUser(registration.getUserId(), token);
                userDto = responseEntity.getBody();
            } catch (HttpClientErrorException httpError) {
                if (httpError.getStatusCode() == HttpStatus.NOT_FOUND) {
                    userDto = null;
                } else if (httpError.getStatusCode() == HttpStatus.NO_CONTENT)
                    userDto = null;
                else if (httpError.getStatusCode().is5xxServerError()) {
                    throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            RegistrationDto dto = new RegistrationDto(registration.getId(), userDto, registration.getProjectName());
            listDto.add(dto);
        }
        return listDto;
    }

    @PostMapping("/new")
    public ResponseEntity<RegistrationDto> save(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody RegistrationDto dto) {
        try {
            ResponseEntity<UserDto> responseUser = userService.getUser(token);
            UserDto userDto;
            if (responseUser.getStatusCode() == HttpStatus.OK)
                userDto = responseUser.getBody();
            else
                userDto = null;

            Registration registration = new Registration(null, userDto.getId(), dto.getProjectName());
            registration = registationService.save(registration);
            dto = new RegistrationDto(registration.getId(), userDto, registration.getProjectName());
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

    }

    @GetMapping("/{id}/byId")
    public ResponseEntity<RegistrationDto> findById(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {
        Registration registration = this.registationService.findById(id);
        UserDto userDto = null;
        try {
            ResponseEntity<UserDto> responseUser = userService.getUser(registration.getUserId(), token);
            userDto = responseUser.getBody();
        } catch (HttpClientErrorException httpClientError) {
            if (httpClientError.getStatusCode() == HttpStatus.NO_CONTENT)
                userDto = null;
            else if (httpClientError.getStatusCode() == HttpStatus.NOT_FOUND)
                userDto = null;
            else if (httpClientError.getStatusCode().is5xxServerError())
                throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        RegistrationDto registrationDto = new RegistrationDto(registration.getId(), userDto, registration.getProjectName());
        return new ResponseEntity<>(registrationDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/byId")
    public ResponseEntity<RegistrationDto> update(
            @PathVariable("id") Integer id,
            @RequestBody RegistrationDto dto,
            @RequestHeader("Authorization") String token) {
        try {
            Registration registration = this.registationService.findById(id);
            ResponseEntity<UserDto> userResponse = userService.getUser(registration.getUserId(), token);
            registration.setProjectName(dto.getProjectName());
            registration = registationService.save(registration);
            dto = new RegistrationDto(registration.getId(), userResponse.getBody(), registration.getProjectName());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (HttpClientErrorException httpClientError) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

}
