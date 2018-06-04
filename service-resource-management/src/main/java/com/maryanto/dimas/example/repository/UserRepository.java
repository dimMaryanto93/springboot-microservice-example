package com.maryanto.dimas.example.repository;

import com.maryanto.dimas.example.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findByEmail(String email);
}
