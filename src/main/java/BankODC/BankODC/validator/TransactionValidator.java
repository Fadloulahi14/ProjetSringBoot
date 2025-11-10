package BankODC.BankODC.validator;

import BankODC.BankODC.dto.request.TransactionRequest;
import BankODC.BankODC.entity.Transaction;
import BankODC.BankODC.exception.TransactionException;

import java.math.BigDecimal;

public class TransactionValidator {

    public static void validateTransactionRequest(TransactionRequest request) {
        if (request == null) {
            throw new TransactionException("INVALID_DATA", "Les données de transaction sont nulles");
        }

        if (!ValidationRules.isValidTransactionAmount(request.getMontant())) {
            throw new TransactionException("INVALID_AMOUNT", ValidationRules.TRANSACTION_AMOUNT_INVALID);
        }

        if (!ValidationRules.isNotEmpty(request.getType())) {
            throw new TransactionException("REQUIRED_FIELD_MISSING", "Le type de transaction est obligatoire");
        }

        if (!isValidTransactionType(request.getType())) {
            throw new TransactionException("INVALID_TRANSACTION_TYPE", "Le type de transaction doit être DEPOSIT, WITHDRAWAL ou TRANSFER");
        }
    }

    public static void validateTransactionEntity(Transaction transaction) {
        if (transaction == null) {
            throw new TransactionException("INVALID_DATA", "L'entité transaction est nulle");
        }

        if (transaction.getMontant() == null || !ValidationRules.isValidTransactionAmount(transaction.getMontant())) {
            throw new TransactionException("INVALID_AMOUNT", ValidationRules.TRANSACTION_AMOUNT_INVALID);
        }

        if (!ValidationRules.isNotEmpty(transaction.getType())) {
            throw new TransactionException("REQUIRED_FIELD_MISSING", "Le type de transaction est obligatoire");
        }

        if (!isValidTransactionType(transaction.getType())) {
            throw new TransactionException("INVALID_TRANSACTION_TYPE", "Le type de transaction doit être DEPOSIT, WITHDRAWAL ou TRANSFER");
        }

        if (transaction.getCompteId() == null) {
            throw new TransactionException("REQUIRED_FIELD_MISSING", "L'ID du compte est obligatoire");
        }
    }

    public static void validateTransfer(TransactionRequest request, BigDecimal sourceBalance) {
        if (request == null) {
            throw new TransactionException("INVALID_DATA", "Les données de transfert sont nulles");
        }

        if (!"TRANSFER".equals(request.getType())) {
            throw new TransactionException("INVALID_OPERATION", "Cette opération n'est pas un transfert");
        }

        if (sourceBalance == null || sourceBalance.compareTo(request.getMontant()) < 0) {
            throw new TransactionException("INSUFFICIENT_BALANCE", "Solde insuffisant pour effectuer le transfert");
        }

        // For transfers, we need additional validation logic in the service layer
        // since TransactionRequest doesn't have source/target account IDs
    }

    public static void validateWithdrawal(TransactionRequest request, BigDecimal currentBalance) {
        if (request == null) {
            throw new TransactionException("INVALID_DATA", "Les données de retrait sont nulles");
        }

        if (!"WITHDRAWAL".equals(request.getType())) {
            throw new TransactionException("INVALID_OPERATION", "Cette opération n'est pas un retrait");
        }

        if (currentBalance == null || currentBalance.compareTo(request.getMontant()) < 0) {
            throw new TransactionException("INSUFFICIENT_BALANCE", "Solde insuffisant pour effectuer le retrait");
        }
    }

    public static void validateDeposit(TransactionRequest request) {
        if (request == null) {
            throw new TransactionException("INVALID_DATA", "Les données de dépôt sont nulles");
        }

        if (!"DEPOSIT".equals(request.getType())) {
            throw new TransactionException("INVALID_OPERATION", "Cette opération n'est pas un dépôt");
        }
    }

    private static boolean isValidTransactionType(String type) {
        return type != null && (type.equals("DEPOSIT") || type.equals("WITHDRAWAL") || type.equals("TRANSFER"));
    }
}