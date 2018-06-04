package com.maryanto.dimas.example.service;

import com.maryanto.dimas.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Value("${userAPI}")
    private String registrationApi;

    @Autowired
    private RestTemplate restTemplate;

    private final static Logger console = LoggerFactory.getLogger(UserService.class);

    public User getUser(String username) {
        String uri = new StringBuilder(registrationApi)
                .append("/api/users/byEmail?email=").append(username).toString();
        User user = restTemplate.getForObject(uri, User.class);
        return user;
    }
}
