package BankODC.BankODC.repository;

import BankODC.BankODC.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Override
    Optional<Transaction> findById(UUID id);
}
