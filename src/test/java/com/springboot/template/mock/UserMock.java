package com.springboot.template.mock;

import com.springboot.template.dto.enuns.RoleEnum;
import com.springboot.template.entity.UserEntity;

public class UserMock {
    private UserMock() {
    }

    public static UserEntity mock() {
        return new UserEntity()
                .setId(1L)
                .setName("Test")
                .setEmail("test@test.com")
                .setPassword("$2a$10$evbmDWzIqp13PpAanPwR4u.M918uOxJ5knvn1EqS8GiZ4nIt0h/IK")
                .setRole(RoleEnum.USER);
    }
}
