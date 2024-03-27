package org.enigma.zooticket.repository;

import jakarta.transaction.Transactional;
import org.enigma.zooticket.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_admin (id, phone, name, status, user_id) VALUES (:#{#admin.id}, :#{#admin.mobilePhone}, :#{#admin.name}, :#{#admin.status.name()}, :#{#admin.user.id});", nativeQuery = true)
    void saveAdmin(Admin admin);
}
