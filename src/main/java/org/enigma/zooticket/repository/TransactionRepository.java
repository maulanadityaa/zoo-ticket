package org.enigma.zooticket.repository;

import jakarta.transaction.Transactional;
import org.enigma.zooticket.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("SELECT t FROM Transaction t WHERE t.customer.id = :customerId")
    List<Transaction> findByCustomerId(String customerId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO t_transaction (id, trans_date, customer_id) VALUES (:#{#transaction.id},:#{#transaction.transDate},:#{#transaction.customer.id});", nativeQuery = true)
    void saveTransaction(Transaction transaction);

    @Transactional
    default void insertAndFlush(Transaction transaction) {
        transaction.setId(UUID.randomUUID().toString());
        saveTransaction(transaction);
        flush();
    }
}
