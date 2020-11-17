package org.template.service;

import org.template.core.exception.NotFoundException;
import org.template.entity.UserEntity;
import org.template.model.request.UserRegisterRequest;
import org.template.model.response.UserResponse;
import org.template.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public void register(UserRegisterRequest request) {
        UserEntity entity = new UserEntity()
                .setId(UUID.randomUUID())
                .setEmail(request.getEmail())
                .setName(request.getName())
                .setPassword(encoder.encode(request.getPassword()))
                .setCreatedAt(ZonedDateTime.now());

        repository.save(entity);
    }

    public UserResponse findByEmail(String email) {
        return repository.findByEmail(email)
                .map(UserResponse::new)
                .orElseThrow(NotFoundException::new);
    }
}
