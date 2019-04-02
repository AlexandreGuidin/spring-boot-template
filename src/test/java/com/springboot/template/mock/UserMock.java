package com.springboot.template.mock;

import com.springboot.template.entity.UserEntity;
import com.springboot.template.model.enuns.RoleEnum;

public class UserMock {
    private UserMock() {
    }

    public static UserEntity mock() {
        return mock(1L);
    }

    public static UserEntity mock(Long id) {
        return new UserEntity()
                .setId(id)
                .setName("Test")
                .setEmail("test@test.com")
                .setPassword("$2a$10$evbmDWzIqp13PpAanPwR4u.M918uOxJ5knvn1EqS8GiZ4nIt0h/IK")
                .setRole(RoleEnum.USER);
    }
}
