package BankODC.BankODC.service;

import BankODC.BankODC.dto.TransactionDTO;
import BankODC.BankODC.entity.Transaction;
import BankODC.BankODC.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<TransactionDTO> getTransactionById(UUID id) {
        return transactionRepository.findById(id)
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class));
    }

    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return modelMapper.map(savedTransaction, TransactionDTO.class);
    }

    public void deleteTransaction(UUID id) {
        transactionRepository.deleteById(id);
    }
}