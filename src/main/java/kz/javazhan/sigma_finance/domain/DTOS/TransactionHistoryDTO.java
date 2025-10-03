package kz.javazhan.sigma_finance.domain.DTOS;

import kz.javazhan.sigma_finance.domain.enums.CurrencyTypeEnum;
import kz.javazhan.sigma_finance.domain.enums.TransactionDirection;
import kz.javazhan.sigma_finance.domain.enums.TransactionStatus;
import kz.javazhan.sigma_finance.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryDTO {
    private UUID id;
    private TransactionType type;
    private TransactionDirection direction;
    private TransactionStatus status;
    private Long amount;
    private CurrencyTypeEnum currency;
    private String description;

    private UUID sourceAccountId;
    private String sourceAccountNumber;
    private UUID targetAccountId;
    private String targetAccountNumber;

    private String counterpartyName;
    private String counterpartyPhone;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

