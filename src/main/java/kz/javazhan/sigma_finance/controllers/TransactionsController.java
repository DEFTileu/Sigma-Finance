package kz.javazhan.sigma_finance.controllers;

import kz.javazhan.sigma_finance.domain.DTOS.SelfTransferRequestDTO;
import kz.javazhan.sigma_finance.domain.DTOS.TransactionDTO;
import kz.javazhan.sigma_finance.domain.DTOS.TransactionHistoryDTO;
import kz.javazhan.sigma_finance.domain.DTOS.TransferRequestDTO;
import kz.javazhan.sigma_finance.domain.entities.User;
import kz.javazhan.sigma_finance.domain.enums.TransactionType;
import kz.javazhan.sigma_finance.mappers.TransactionMapper;
import kz.javazhan.sigma_finance.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionsController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping("")
    public ResponseEntity<?> getAllTransactions() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TransactionDTO> transactionDTOS =  transactionService.getTransactionsByUserId(user.getId()).stream().map(
                transactionMapper::toDTO
        ).toList();

        return ResponseEntity.ok(transactionDTOS);
    }



    @GetMapping("/{id}")
    public ResponseEntity<TransactionHistoryDTO> getTransactionById(@PathVariable UUID id) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TransactionHistoryDTO transactionDTO = transactionService.getTransactionByIdAndUserId(id, user.getId());

        return ResponseEntity.ok(transactionDTO);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionHistoryDTO>> getTransactionHistory(
            @RequestParam(required = false) TransactionType type
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TransactionHistoryDTO> history = transactionService.getTransactionHistory(user.getId(), type);

        return ResponseEntity.ok(history);
    }

    @PostMapping("/phone")
    public ResponseEntity<TransactionHistoryDTO> transferByPhone(@RequestBody TransferRequestDTO transactionDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TransactionHistoryDTO dto = transactionService.transferByPhoneAndReturnHistory(transactionDTO, user.getId());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/self-transfer")
    public ResponseEntity<TransactionHistoryDTO> transferBySelfTransfer(@RequestBody SelfTransferRequestDTO requestDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TransactionHistoryDTO dto = transactionService.selfTransferFromDTOAndReturnHistory(requestDTO, user.getId());
        return ResponseEntity.ok(dto);
    }
}
