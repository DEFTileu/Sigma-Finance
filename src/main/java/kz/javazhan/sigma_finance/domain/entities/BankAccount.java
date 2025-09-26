package kz.javazhan.sigma_finance.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import kz.javazhan.sigma_finance.domain.enums.AccountStatusEnum;
import kz.javazhan.sigma_finance.domain.enums.AccountType;
import kz.javazhan.sigma_finance.domain.enums.CurrencyTypeEnum;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@ToString
public class BankAccount extends Account{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, updatable = false, nullable = false)
    private UUID id;


    @Column(nullable = false, unique = true, updatable = false)
    private String accountNumber;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyTypeEnum currency;

    @Column(nullable = false)
    private AccountStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    @JsonIgnoreProperties({"accounts"})
    private User owner;


    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Transaction> transactions;


    @CreatedDate
    private LocalDateTime createdAt;


    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Double.compare(balance, that.balance) == 0 && Objects.equals(id, that.id) && Objects.equals(accountNumber, that.accountNumber) && accountType == that.accountType && currency == that.currency && status == that.status && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, accountType, balance, currency, status, createdAt, updatedAt);
    }
}
