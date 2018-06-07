package com.maryanto.dimas.example.controller;

import com.maryanto.dimas.example.dto.UserDto;
import com.maryanto.dimas.example.entity.User;
import com.maryanto.dimas.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final static Logger console = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{id}")
    public ResponseEntity<User> findByEmail(@PathVariable("id") Integer id) {
        User user = userRepository.findOne(id);
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/me")
    public ResponseEntity<User> findByEmail(Principal principal) {
        console.info("user logged in : {}", principal.getName());
        User user = userRepository.findByEmail(principal.getName());
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/byEmail")
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        console.info("params: {}", email);
        User user = userRepository.findByEmail(email);
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * menggunakan validasi
     *
     * @param userDto
     * @param principal
     * @return
     */
    @PostMapping("/submited")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto, Principal principal) {
        User user = new User(
                null,
                userDto.getEmail(),
                userDto.getPassword(),
                true,
                Timestamp.valueOf(LocalDateTime.now()),
                principal.getName(),
                null,
                null
        );

        user = userRepository.save(user);
        if (user.getId() != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * tanpa menggunakan validasi
     *
     * @param userDto
     * @param principal
     * @return
     */
    @PostMapping("/created")
    public ResponseEntity<User> saveUser(@RequestBody UserDto userDto, Principal principal) {
        User user = new User(
                null,
                userDto.getEmail(),
                userDto.getPassword(),
                true,
                Timestamp.valueOf(LocalDateTime.now()),
                principal.getName(),
                null,
                null
        );

        user = userRepository.save(user);
        if (user.getId() != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UserDto userDto, Principal principal) {
        User user = new User(
                id,
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.isEnabled(),
                userDto.getCreatedDate(),
                userDto.getCreatedBy(),
                Timestamp.valueOf(LocalDateTime.now()),
                principal.getName()
        );
        user = userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") User user) {
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
