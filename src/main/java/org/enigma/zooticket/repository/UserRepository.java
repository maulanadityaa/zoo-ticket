package org.enigma.zooticket.repository;

import jakarta.transaction.Transactional;
import org.enigma.zooticket.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    @Modifying
    @Query(value = "INSERT INTO m_user (id, username, password, role_id) VALUES (:#{#user.id}, :#{#user.username}, :#{#user.password}, :#{#user.role.id});", nativeQuery = true)
    void saveUser(User user);

    @Transactional
    default void insertAndFlush(User user) {
        user.setId(UUID.randomUUID().toString());
        saveUser(user);
        flush();
    }
}
