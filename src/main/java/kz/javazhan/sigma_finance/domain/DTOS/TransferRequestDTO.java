package kz.javazhan.sigma_finance.domain.DTOS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDTO {
    private UUID sourceAccountId;
    private UUID destinationAccountId;
    private BigDecimal amount;
    private String description;
}
