package org.enigma.zooticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.model.entity.AppUser;
import org.enigma.zooticket.model.entity.User;
import org.enigma.zooticket.repository.UserRepository;
import org.enigma.zooticket.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public AppUser loadUserByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

        return AppUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole().getName())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

        return AppUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole().getName())
                .build();
    }
}
