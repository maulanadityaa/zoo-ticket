package org.enigma.zooticket.service;

import org.enigma.zooticket.model.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadUserByUserId(String userId);
}
