package BankODC.BankODC.service;

import BankODC.BankODC.dto.TransactionDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITransactionService {
    List<TransactionDTO> getAllTransactions();
    Optional<TransactionDTO> getTransactionById(UUID id);
    TransactionDTO saveTransaction(TransactionDTO transactionDTO);
    void deleteTransaction(UUID id);
}