package com.springboot.template.service;

import com.springboot.template.model.HealthModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HealthServiceTest {

    @Autowired
    private HealthService service;

    @Test
    public void get() {
        HealthModel healthDTO = service.get();

        assertThat(healthDTO.getStatus()).isEqualTo("UP");
        assertThat(healthDTO.getTime()).isNotNull();
    }
}
