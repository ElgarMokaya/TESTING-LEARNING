package com.example.JUNIT_TESTING.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    User user;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

    }
    @Test
    void testFullName(){
        assertEquals("John Doe", user.getFullName());

    }


}
