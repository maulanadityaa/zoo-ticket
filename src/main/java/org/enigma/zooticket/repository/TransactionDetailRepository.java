package org.enigma.zooticket.repository;

import jakarta.transaction.Transactional;
import org.enigma.zooticket.model.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO t_transaction_detail (id, transaction_id, ticket_id, quantity, created_at) VALUES (:#{#transactionDetail.id}, :#{#transactionDetail.transaction.id}, :#{#transactionDetail.ticket.id}, :#{#transactionDetail.quantity}, :#{#transactionDetail.createdAt});", nativeQuery = true)
    void saveTransactionDetail(TransactionDetail transactionDetail);
}
