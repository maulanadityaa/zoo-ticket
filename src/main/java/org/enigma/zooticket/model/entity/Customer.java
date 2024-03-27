package org.enigma.zooticket.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.enigma.zooticket.constant.EStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "m_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "phone", nullable = false, unique = true)
    private String mobilePhone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "dob")
    private Date dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EStatus status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", status=" + status +
                ", user=" + user +
                ", transactions=" + transactions +
                '}';
    }
}
