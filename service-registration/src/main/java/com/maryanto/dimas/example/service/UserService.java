package com.maryanto.dimas.example.service;

import com.maryanto.dimas.example.dto.UserDto;
import com.maryanto.dimas.example.repository.RegistrationRepository;
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

    public UserDto getUser(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(headers);
        String uri = new StringBuilder(registrationApi)
                .append("/api/users/me").toString();
        ResponseEntity<UserDto> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, UserDto.class);
        return responseEntity.getBody();
    }

    public UserDto createUser(UserDto user) {
        String uri = new StringBuilder(registrationApi)
                .append("/api/users/created").toString();
        ResponseEntity<UserDto> responseEntity = restTemplate.postForEntity(uri, user, UserDto.class);
        return responseEntity.getBody();
    }

    public UserDto updateUser(Integer id, UserDto user, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(user, headers);

        String uri = new StringBuilder(registrationApi)
                .append("/api/users/").append(id).toString();
        ResponseEntity<UserDto> userResponse = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, UserDto.class);
        return userResponse.getBody();
    }
}
