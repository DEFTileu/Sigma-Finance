package kz.javazhan.sigma_finance.domain.entities;

import jakarta.persistence.*;
import kz.javazhan.sigma_finance.domain.enums.CurrencyTypeEnum;
import kz.javazhan.sigma_finance.domain.enums.TransactionStatus;
import kz.javazhan.sigma_finance.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @Column(nullable = false,updatable = false,unique = true)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,updatable = false)
    private TransactionType transactionType;

    @Column(nullable = false,updatable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,updatable = false)
    private CurrencyTypeEnum currency;

    @ManyToOne()
    @JoinColumn(nullable = false, updatable = false)
    private BankAccount sourceAccount;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private BankAccount targetAccount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(updatable = false)
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(id, that.id) && transactionType == that.transactionType && currency == that.currency && status == that.status && Objects.equals(description, that.description) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionType, amount, currency, status, description, createdAt, updatedAt);
    }
}
