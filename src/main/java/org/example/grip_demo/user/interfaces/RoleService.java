package org.example.grip_demo.user.interfaces;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.user.domain.Role;
import org.example.grip_demo.user.infrastructure.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findRoleByName(String name) {
        Optional<Role> roleUser = roleRepository.findByName("ROLE_USER");
        return roleUser.get();
    }
}
