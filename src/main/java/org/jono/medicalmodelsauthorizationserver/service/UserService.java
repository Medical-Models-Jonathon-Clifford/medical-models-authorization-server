package org.jono.medicalmodelsauthorizationserver.service;

import java.util.Optional;
import org.jono.medicalmodelsauthorizationserver.model.User;
import org.jono.medicalmodelsauthorizationserver.repository.jdbc.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(final User user) {
        return userRepository.create(user);
    }

    public Optional<User> getById(final String id) {
        return userRepository.findById(id);
    }
}