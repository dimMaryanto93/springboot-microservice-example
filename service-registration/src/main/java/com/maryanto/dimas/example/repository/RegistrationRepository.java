package com.maryanto.dimas.example.repository;

import com.maryanto.dimas.example.entity.Registration;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RegistrationRepository extends PagingAndSortingRepository<Registration, Integer> {
}
