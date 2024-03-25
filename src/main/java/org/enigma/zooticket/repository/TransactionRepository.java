package org.enigma.zooticket.repository;

import org.enigma.zooticket.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query("SELECT t FROM Transaction t WHERE t.customer = :customerId")
    Optional<Transaction> findByCustomerId(String customerId);
}
