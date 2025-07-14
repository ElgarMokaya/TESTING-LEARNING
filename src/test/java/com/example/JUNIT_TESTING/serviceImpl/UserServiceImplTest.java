package com.example.JUNIT_TESTING.serviceImpl;

import com.example.JUNIT_TESTING.dto.UserCreationRequest;
import com.example.JUNIT_TESTING.dto.UserCreationResponse;
import com.example.JUNIT_TESTING.dto.UserResponse;
import com.example.JUNIT_TESTING.dto.UserUpdateRequest;
import com.example.JUNIT_TESTING.model.User;
import com.example.JUNIT_TESTING.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testGetUserById() {
        var userCreationRequest =  buildUserCreationRequest();
        //arrange
        UUID userId = UUID.randomUUID();
        User user = buildUser(userId);

        User savedUser =userRepository.save(user);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponse userResponse =        buildUserResponse(user);
        //act
        UserResponse actualResponse=userService.getUserById(userId);
        //Assert
        verify(userRepository,times(1)).findById(userId);
        assertNotNull(actualResponse);
        assertEquals(userService.getUserById(userId), actualResponse);

        assertEquals(userResponse.getFirstName(), actualResponse.getFirstName());

    }
    @Test
    void testUserFindByIdNotFound() {
        var id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserById(id));
        verify(userRepository, times(1)).findById(id);
        assertEquals("User not found", exception.getMessage());
    }
    @Test
    void testCreateUser(){
        //arrange
        var userCreationRequest =  buildUserCreationRequest();

        User user = buildUser(UUID.randomUUID());


       when(userRepository.save(any(User.class))).thenReturn(user);
       //act
       var  newUserCreationResponse=userService.createUser(userCreationRequest);
       //assert
       verify(userRepository,times(1)).save(any(User.class));
       assertNotNull(newUserCreationResponse);
       assertEquals(newUserCreationResponse.getEmail(),user.getEmail());

    }
    @Test
    void testDeleteUserById(){
        //arrange
        var userId = UUID.randomUUID();

        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);
        //act
        userService.deleteUser(userId);
       //assert
        verify(userRepository,times(1)).existsById(userId);
        verify(userRepository,times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserNotFound(){
        var  id = UUID.randomUUID();
        when(userRepository.existsById(id)).thenReturn(false);
         RuntimeException exception=assertThrows(RuntimeException.class, () -> userService.deleteUser(id));

         assertEquals("User not found", exception.getMessage());

         verify(userRepository,times(1)).existsById(id);
         verify(userRepository,times(0)).deleteById(id);

    }
    @Test
    void testUpdateUserNotFound(){
        var userId = UUID.randomUUID();
        var userCreationRequest = buildUserUpdateRequest();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        RuntimeException exception=assertThrows(RuntimeException.class, () -> userService.updateUser(userId, userCreationRequest));
        assertEquals("User not found", exception.getMessage());

        verify(userRepository,times(1)).findById(userId);
        verify(userRepository,times(0)).save(any(User.class));
    }
    @Test
    void testFetchAllUsers(){
        var user = buildUser(UUID.randomUUID());
        when(userRepository.findAll()).thenReturn(List.of(user));

        var userResponse = userService.getAllUsers();

        verify(userRepository,times(1)).findAll();
        verify(userRepository,times(0)).save(any(User.class));
        assertEquals("female",userResponse.get(0).getGender());

    }
    @Test
    void testUpdateUser(){
        var userId = UUID.randomUUID();
        var user = buildUser(userId);
        var userUpdateRequest=buildUserUpdateRequest();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        var response = userService.updateUser(userId, userUpdateRequest);

        verify(userRepository,times(1)).findById(userId);
        verify(userRepository,times(1)).save(any(User.class));
        assertEquals("maya",response.getFirstName());

    }


    //Utility classes

    private UserCreationRequest buildUserCreationRequest() {
        return UserCreationRequest.builder()
                .firstName("elgar")
                .lastName("mokaya")
                .email("elgar@gmail.com")
                .gender("female")
                .password("password")
                .build();
    }
    private UserCreationResponse buildUserCreationResponse(User user) {
        return UserCreationResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .gender(user.getGender())
                .build();
    }
    private UserResponse buildUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .build();
    }

    private User buildUser(UUID id) {
        UserCreationRequest req = buildUserCreationRequest();
        return User.builder()
                .id(id)
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .password(req.getPassword())
                .gender(req.getGender())
                .build();
    }
    private UserUpdateRequest buildUserUpdateRequest() {
        return UserUpdateRequest.builder().firstName("maya").lastName("wambua").gender("male").build();
    }


}