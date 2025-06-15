package com.example.JUNIT_TESTING.serviceImpl;

import com.example.JUNIT_TESTING.dto.UserCreationRequest;
import com.example.JUNIT_TESTING.dto.UserCreationResponse;
import com.example.JUNIT_TESTING.dto.UserResponse;
import com.example.JUNIT_TESTING.dto.UserUpdateRequest;
import com.example.JUNIT_TESTING.model.User;
import com.example.JUNIT_TESTING.repository.UserRepository;
import com.example.JUNIT_TESTING.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Override
    public UserCreationResponse createUser(UserCreationRequest userCreationRequest) {
        User user = User.builder()
                .firstName(userCreationRequest.getFirstName())
                .lastName(userCreationRequest.getLastName())
                .email(userCreationRequest.getEmail())
                .password(userCreationRequest.getPassword())
                .gender(userCreationRequest.getGender())
                .build();
        User newUser = userRepository.save(user);

        UserCreationResponse response = UserCreationResponse.builder()
                .email(newUser.getEmail())
                .firstName(newUser.getFirstName())
                .gender(newUser.getGender())
                .build();
        return response;
    }
    @Override
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public UserResponse updateUser(UUID id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());

        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }
    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .build();
    }
}
