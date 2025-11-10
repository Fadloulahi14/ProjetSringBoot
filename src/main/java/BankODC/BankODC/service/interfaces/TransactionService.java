package BankODC.BankODC.service.interfaces;

import BankODC.BankODC.dto.request.TransactionRequest;
import BankODC.BankODC.dto.response.TransactionResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionService {
    // Legacy methods (backward compatibility)
    List<TransactionResponse> getAllTransactions();
    Optional<TransactionResponse> getTransactionById(UUID id);
    TransactionResponse saveTransaction(TransactionResponse transactionDTO);
    void deleteTransaction(UUID id);

    // New methods for separated DTOs
    List<TransactionResponse> getAllTransactionsResponse();
    Optional<TransactionResponse> getTransactionByIdResponse(UUID id);
    TransactionResponse saveTransactionFromRequest(TransactionRequest transactionRequest);
    TransactionResponse updateTransactionFromRequest(UUID id, TransactionRequest transactionRequest);
}