package org.enigma.zooticket.repository;

import org.enigma.zooticket.constant.ERole;
import org.enigma.zooticket.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
