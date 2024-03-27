package org.enigma.zooticket.repository;

import jakarta.transaction.Transactional;
import org.enigma.zooticket.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_customer (id, full_name, dob, email, phone, status, user_id) VALUES (:#{#customer.id}, :#{#customer.fullName}, :#{#customer.dob}, :#{#customer.email}, :#{#customer.mobilePhone}, :#{#customer.status.name()}, :#{#customer.user.id});", nativeQuery = true)
    void saveCustomer(Customer customer);

    @Modifying
    @Transactional
    @Query(value = "UPDATE m_customer SET full_name = :#{#customer.fullName}, dob = :#{#customer.dob}, email = :#{#customer.email}, phone = :#{#customer.mobilePhone}, status = :#{#customer.status.name()} WHERE id = :#{#customer.id}", nativeQuery = true)
    void updateCustomer(Customer customer);
}
