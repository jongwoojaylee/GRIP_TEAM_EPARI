package org.example.grip_demo.demo;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.user.domain.User;
import org.example.grip_demo.user.infrastructure.UserRepository;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
            userBuilder.password(user.getPassword());
            userBuilder.roles(user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new));
            return userBuilder.build();
        } else {
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }

    }


}
