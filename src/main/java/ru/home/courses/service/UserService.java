package ru.home.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.home.courses.dto.CreateUser;
import ru.home.courses.dto.UserDTO;
import ru.home.courses.entity.Role;
import ru.home.courses.entity.User;
import ru.home.courses.mapper.CreateUserMapper;
import ru.home.courses.mapper.UserMapper;
import ru.home.courses.repository.CourseRepository;
import ru.home.courses.repository.RoleRepository;
import ru.home.courses.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CourseRepository courseRepository;
    private final UserMapper userMapper;
    private final CreateUserMapper createUserMapper;


    public void registerUser(CreateUser createUser) {
        User user = createUserMapper.toEntity(createUser, courseRepository);

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(userRole);

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        userRepository.save(user);
    }

    public boolean loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, user.getPassword());
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOs(users);
    }
}