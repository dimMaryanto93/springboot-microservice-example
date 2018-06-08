package com.maryanto.dimas.example.service;

import com.maryanto.dimas.example.entity.Registration;
import com.maryanto.dimas.example.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService {

    @Autowired
    private RegistrationRepository repository;

    @Transactional
    public Registration save(Registration registration) {
        return repository.save(registration);
    }

    public Registration findById(Integer id) {
        return repository.findOne(id);
    }
}
