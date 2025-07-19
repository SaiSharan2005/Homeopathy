package com.G19.hospital;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class TestRunner {

    @Test
    void contextLoads() {
        // This test ensures that the Spring context loads successfully
        // It will run all the configuration and verify that all beans are properly configured
    }
} 