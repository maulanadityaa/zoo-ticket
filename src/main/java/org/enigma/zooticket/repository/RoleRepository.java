package org.enigma.zooticket.repository;

import org.enigma.zooticket.constant.ERole;
import org.enigma.zooticket.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(ERole name);

    @Query(value = "INSERT INTO m_role (id, name) VALUES (:#{#role.id}, :#{#role.name.name()});", nativeQuery = true)
    void insertRole(Role role);
}
