package kz.javazhan.sigma_finance.mappers;

import kz.javazhan.sigma_finance.domain.DTOS.TransactionDTO;
import kz.javazhan.sigma_finance.domain.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDTO toDTO(Transaction transaction);

    Transaction toEntity(TransactionDTO dto);
}
