package com.example.JUNIT_TESTING.repository;

import com.example.JUNIT_TESTING.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.support.WebContentGenerator;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback(true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        // Given
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .password("securePassword")
                .gender("Female")
                .build();
        // When
         userRepository.save(user);
        User savedUser = userRepository.findById(user.getId()).get();
        // Then
        assertNotNull(savedUser.getId());
        assertEquals("Jane", savedUser.getFirstName());
        assertEquals("jane@example.com", savedUser.getEmail());
        assertEquals("Female", savedUser.getGender());
    }
    @Test
    void testFindByUsername() {
        //given
        String username = "Joan";
        User newUser=User.builder()
                .id(UUID.randomUUID())
                .firstName("Jane")
                .lastName("Doe")
                .email("elgar@gamil.com")
                .username(username)
                .password("securePassword")
                .gender("Female")
                .build();
       userRepository.save(newUser);
        Optional<User> foundUser=userRepository.findByUsername(username);
        //assert
        assertTrue(foundUser.isPresent());
        assertEquals("Doe",foundUser.get().getLastName());
        assertEquals("Jane",foundUser.get().getFirstName());

    }
}
