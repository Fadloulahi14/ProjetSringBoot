package BankODC.BankODC.service.impl;

import BankODC.BankODC.dto.request.TransactionRequest;
import BankODC.BankODC.dto.response.TransactionResponse;
import BankODC.BankODC.entity.Transaction;
import BankODC.BankODC.repository.TransactionRepository;
import BankODC.BankODC.service.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Legacy methods (backward compatibility)
    public List<TransactionResponse> getAllTransactions() {
        return getAllTransactionsResponse();
    }

    public Optional<TransactionResponse> getTransactionById(UUID id) {
        return getTransactionByIdResponse(id);
    }

    public TransactionResponse saveTransaction(TransactionResponse transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.getId());
        transaction.setCompteId(transactionDTO.getCompteId());
        transaction.setType(transactionDTO.getType());
        transaction.setMontant(transactionDTO.getMontant());
        transaction.setDate(transactionDTO.getDate());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionResponse(savedTransaction.getId(), savedTransaction.getCompteId(),
            savedTransaction.getType(), savedTransaction.getMontant(), savedTransaction.getDate());
    }

    public void deleteTransaction(UUID id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<TransactionResponse> getAllTransactionsResponse() {
        return transactionRepository.findAll()
                .stream()
                .map(transaction -> new TransactionResponse(transaction.getId(), transaction.getCompteId(),
                    transaction.getType(), transaction.getMontant(), transaction.getDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TransactionResponse> getTransactionByIdResponse(UUID id) {
        return transactionRepository.findById(id)
                .map(transaction -> new TransactionResponse(transaction.getId(), transaction.getCompteId(),
                    transaction.getType(), transaction.getMontant(), transaction.getDate()));
    }

    @Override
    public TransactionResponse saveTransactionFromRequest(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setCompteId(transactionRequest.getCompteId());
        transaction.setType(transactionRequest.getType());
        transaction.setMontant(transactionRequest.getMontant());
        transaction.setDate(java.time.LocalDateTime.now().toString());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionResponse(savedTransaction.getId(), savedTransaction.getCompteId(),
            savedTransaction.getType(), savedTransaction.getMontant(), savedTransaction.getDate());
    }

    @Override
    public TransactionResponse updateTransactionFromRequest(UUID id, TransactionRequest transactionRequest) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow();
        transaction.setCompteId(transactionRequest.getCompteId());
        transaction.setType(transactionRequest.getType());
        transaction.setMontant(transactionRequest.getMontant());

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return new TransactionResponse(updatedTransaction.getId(), updatedTransaction.getCompteId(),
            updatedTransaction.getType(), updatedTransaction.getMontant(), updatedTransaction.getDate());
    }
}