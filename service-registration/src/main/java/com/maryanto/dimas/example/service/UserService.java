package com.maryanto.dimas.example.service;

import com.maryanto.dimas.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Value("${userAPI}")
    private String registrationApi;

    private final static Logger console = LoggerFactory.getLogger(UserService.class);

    public User getUser(String email, String password) {
        RestTemplate http = new RestTemplate();
        String uri = new StringBuilder(registrationApi)
                .append("/api/users/")
                .append(email)
                .append("?password=").append(password).toString();
        console.info("uri : {}", uri);
        User user = http.getForObject(uri, User.class);
        return user;
    }
}
