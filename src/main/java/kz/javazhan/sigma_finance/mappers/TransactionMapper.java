package kz.javazhan.sigma_finance.mappers;

import kz.javazhan.sigma_finance.domain.DTOS.TransactionDTO;
import kz.javazhan.sigma_finance.domain.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mappings({
            @Mapping(source = "transactionType", target = "type"),
            @Mapping(source = "sourceAccount.id", target = "sourceAccountId"),
            @Mapping(source = "targetAccount.id", target = "destinationAccountId")
    })
    TransactionDTO toDTO(Transaction transaction);

    @Mappings({
            @Mapping(source = "type", target = "transactionType")
    })
    Transaction toEntity(TransactionDTO dto);
}
