package org.example.grip_demo.user.interfaces;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.user.domain.RegisterUserDTO;
import org.example.grip_demo.user.domain.Role;
import org.example.grip_demo.user.domain.User;
import org.example.grip_demo.user.infrastructure.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public boolean isUsernameExist(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    public boolean isUserEmailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public boolean isUserNickNameExist(String NickName) {
        Optional<User> user = userRepository.findByNickName(NickName);
        return user.isPresent();
    }

    public User createUser(User user) {
        Role role = roleService.findRoleByName("ROLE_USER");
        if(role==null)
            throw new RuntimeException("Role not found");
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public User updateUser(User user) {
        Role role = roleService.findRoleByName("ROLE_USER");
        if(role==null)
            throw new RuntimeException("Role not found");
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User registerUserDTOToUser(RegisterUserDTO registerUserDTO){
        User user = new User();
        user.setUsername(registerUserDTO.getUsername());
        user.setPassword(registerUserDTO.getPassword());
        user.setName(registerUserDTO.getName());
        user.setNickName(registerUserDTO.getNickName());
        user.setEmail(registerUserDTO.getEmail());
        user.setAddress(registerUserDTO.getAddress());
        user.setPhoneNumber(registerUserDTO.getPhoneNumber());
        user.setGender(registerUserDTO.getGender());
        return user;
    }





}
