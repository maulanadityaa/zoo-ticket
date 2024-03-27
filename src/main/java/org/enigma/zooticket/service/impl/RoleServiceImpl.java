package org.enigma.zooticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.constant.ERole;
import org.enigma.zooticket.model.entity.Role;
import org.enigma.zooticket.model.exception.ApplicationException;
import org.enigma.zooticket.repository.RoleRepository;
import org.enigma.zooticket.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
                .id(UUID.randomUUID().toString())
                .name(role)
                .build();
        roleRepository.insertRole(currentRole);

        return currentRole;
    }

    @Override
    public Role getByName(ERole name) {
        return roleRepository.findByName(name).orElseThrow(() -> new ApplicationException("Role not found", String.format("Role with name=%s", name.name()), HttpStatus.NOT_FOUND));
    }
}
