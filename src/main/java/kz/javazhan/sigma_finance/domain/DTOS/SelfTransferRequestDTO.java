package kz.javazhan.sigma_finance.domain.DTOS;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SelfTransferRequestDTO {
    private UUID fromAccountId;
    private UUID toAccountId;
    private Long amount;
}
