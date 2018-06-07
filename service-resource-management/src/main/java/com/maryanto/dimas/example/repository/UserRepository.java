package com.maryanto.dimas.example.repository;

import com.maryanto.dimas.example.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findByEmail(String email);

    @Modifying
    @Query("update User set email = ?1 where id = ?2")
    Integer updateEmail(String email, Integer id);
}
