package org.enigma.zooticket.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.enigma.zooticket.constant.DbPath;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = DbPath.TICKET_SCHEMA)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "stock", nullable = false, columnDefinition = "int check (stock > 0)")
    private Integer stock;

    @Column(name = "valid_at", nullable = false)
    private Date validAt;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;
}
