package com.springboot.template.service;

import com.springboot.template.entity.UserEntity;
import com.springboot.template.model.enuns.RoleEnum;
import com.springboot.template.model.request.UserRegisterModel;
import com.springboot.template.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder encoder;

    public UserService(UserRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public void register(UserRegisterModel request) {

        UserEntity entity = new UserEntity()
                .setEmail(request.getEmail())
                .setName(request.getName())
                .setPassword(encoder.encode(request.getPassword()))
                .setRole(RoleEnum.USER);

        repository.save(entity);
    }
}
