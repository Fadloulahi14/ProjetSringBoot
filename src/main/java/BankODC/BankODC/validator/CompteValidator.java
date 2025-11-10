package BankODC.BankODC.validator;

import BankODC.BankODC.dto.request.CompteRequest;
import BankODC.BankODC.entity.Compte;
import BankODC.BankODC.exception.CompteException;

public class CompteValidator {

    public static void validateCompteRequest(CompteRequest request) {
        if (request == null) {
            throw new CompteException("INVALID_DATA", "Les données du compte sont nulles");
        }

        if (!ValidationRules.isValidAccountType(request.getType())) {
            throw new CompteException("INVALID_ACCOUNT_TYPE", ValidationRules.ACCOUNT_TYPE_INVALID);
        }

        if (!ValidationRules.isValidBalance(request.getSolde())) {
            throw new CompteException("INVALID_BALANCE", ValidationRules.BALANCE_INVALID);
        }

        if (request.getDevise() != null && !ValidationRules.isValidCurrency(request.getDevise())) {
            throw new CompteException("INVALID_CURRENCY", ValidationRules.CURRENCY_INVALID);
        }
    }

    public static void validateCompteEntity(Compte compte) {
        if (compte == null) {
            throw new CompteException("INVALID_DATA", "L'entité compte est nulle");
        }

        if (!ValidationRules.isNotEmpty(compte.getType())) {
            throw new CompteException("REQUIRED_FIELD_MISSING", "Le type de compte est obligatoire");
        }

        if (!ValidationRules.isValidAccountType(compte.getType())) {
            throw new CompteException("INVALID_ACCOUNT_TYPE", ValidationRules.ACCOUNT_TYPE_INVALID);
        }

        if (compte.getSolde() == null || !ValidationRules.isValidBalance(compte.getSolde())) {
            throw new CompteException("INVALID_BALANCE", ValidationRules.BALANCE_INVALID);
        }

        if (compte.getDevise() != null && !ValidationRules.isValidCurrency(compte.getDevise())) {
            throw new CompteException("INVALID_CURRENCY", ValidationRules.CURRENCY_INVALID);
        }

        if (compte.getTitulaire() != null && !ValidationRules.isValidName(compte.getTitulaire())) {
            throw new CompteException("INVALID_NAME", ValidationRules.NAME_INVALID);
        }
    }

    public static void validateAccountNumber(String accountNumber) {
        if (!ValidationRules.isValidAccountNumber(accountNumber)) {
            throw new CompteException("INVALID_ACCOUNT_NUMBER", ValidationRules.ACCOUNT_NUMBER_INVALID);
        }
    }

    public static void validateTransactionAmount(java.math.BigDecimal amount) {
        if (!ValidationRules.isValidTransactionAmount(amount)) {
            throw new CompteException("INVALID_TRANSACTION_AMOUNT", ValidationRules.TRANSACTION_AMOUNT_INVALID);
        }
    }
}