package com.maryanto.dimas.example.service;

import com.maryanto.dimas.example.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Value("${userAPI}")
    private String registrationApi;

    @Autowired
    private RestTemplate restTemplate;

    private final static Logger console = LoggerFactory.getLogger(UserService.class);

    public User getUser(String name, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<User> httpEntity = new HttpEntity<>(headers);
        String uri = new StringBuilder(registrationApi)
                .append("/api/users/me").toString();
        ResponseEntity<User> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, User.class);
        return responseEntity.getBody();
    }

    public User createUser(User user) {
        String uri = new StringBuilder(registrationApi)
                .append("/api/users/created").toString();
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(uri, user, User.class);
        return responseEntity.getBody();
    }

    public User updateUser(Integer id, User user, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<User> httpEntity = new HttpEntity<>(user, headers);

        String uri = new StringBuilder(registrationApi)
                .append("/api/users/").append(id).toString();
        ResponseEntity<User> userResponse = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, User.class);
        return userResponse.getBody();
    }
}
