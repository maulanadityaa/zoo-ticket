package org.enigma.zooticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.constant.ERole;
import org.enigma.zooticket.model.entity.Role;
import org.enigma.zooticket.repository.RoleRepository;
import org.enigma.zooticket.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(ERole role) {
        Optional<Role> optionalRole = roleRepository.findByName(role);

        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }

        Role currentRole = Role.builder()
                .name(role)
                .build();
        return roleRepository.save(currentRole);
    }

    @Override
    public Role getByName(ERole name) {
        return roleRepository.findByName(name).orElse(null);
    }
}
