package com.maryanto.dimas.example.service;

import com.maryanto.dimas.example.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Value("${userAPI}")
    private String registrationApi;

    @Autowired
    private RestTemplate restTemplate;

    private final static Logger console = LoggerFactory.getLogger(UserService.class);

    /**
     * find user by current login
     *
     * @param token sending bearer access_token from oauth2 provider
     * @return
     * @throws HttpClientErrorException
     */
    public ResponseEntity<UserDto> getUser(String token) throws HttpClientErrorException {
        // create header for security authorization
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(headers);

        String uri = new StringBuilder(registrationApi).append("/api/users/me").toString();
        // call http client get method
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, UserDto.class);
    }

    /**
     * find user by id
     *
     * @param id    id of users
     * @param token sending bearer access token form oauth2 provinder
     * @return
     * @throws HttpClientErrorException
     */
    public ResponseEntity<UserDto> getUser(Integer id, String token) throws HttpClientErrorException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(headers);
        String uri = new StringBuilder(registrationApi)
                .append("/api/users/{id}").toString();
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, UserDto.class, id);
    }

    public ResponseEntity<UserDto> createUser(UserDto user) throws HttpClientErrorException {
        String uri = new StringBuilder(registrationApi)
                .append("/api/users/created").toString();
        return restTemplate.postForEntity(uri, user, UserDto.class);
    }

    public ResponseEntity<UserDto> updateUser(Integer id, UserDto user, String token) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(user, headers);

        String uri = new StringBuilder(registrationApi)
                .append("/api/users/").append(id).toString();
        return restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, UserDto.class);
    }
}
