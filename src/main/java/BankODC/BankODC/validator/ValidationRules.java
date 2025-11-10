package BankODC.BankODC.validator;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ValidationRules {

    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    // Phone validation pattern (for Senegal format)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+221|221)?[76-8][0-9]{7}$");

    // Account number pattern (alphanumeric, 8-20 characters)
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[A-Z0-9]{8,20}$");

    // Password minimum length
    private static final int MIN_PASSWORD_LENGTH = 6;

    // Name validation (letters, spaces, hyphens, apostrophes)
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-zÀ-ÿ\\s\\-']+$");

    // Minimum balance for accounts
    private static final BigDecimal MIN_BALANCE = BigDecimal.ZERO;

    // Maximum balance (to prevent unrealistic values)
    private static final BigDecimal MAX_BALANCE = new BigDecimal("10000000"); // 10 million

    // Transaction amount limits
    private static final BigDecimal MIN_TRANSACTION_AMOUNT = new BigDecimal("100"); // 100 FCFA minimum
    private static final BigDecimal MAX_TRANSACTION_AMOUNT = new BigDecimal("5000000"); // 5 million FCFA maximum

    // Validation methods

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isValidBalance(BigDecimal balance) {
        return balance != null && balance.compareTo(MIN_BALANCE) >= 0 && balance.compareTo(MAX_BALANCE) <= 0;
    }

    public static boolean isValidTransactionAmount(BigDecimal amount) {
        return amount != null && amount.compareTo(MIN_TRANSACTION_AMOUNT) >= 0 && amount.compareTo(MAX_TRANSACTION_AMOUNT) <= 0;
    }

    public static boolean isValidAccountType(String type) {
        return type != null && (type.equals("CHEQUE") || type.equals("EPARGNE") || type.equals("epargne") || type.equals("chèque"));
    }

    public static boolean isValidCurrency(String currency) {
        return currency != null && currency.equals("FCFA");
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidLength(String value, int minLength, int maxLength) {
        return value != null && value.length() >= minLength && value.length() <= maxLength;
    }

    // Error messages
    public static final String EMAIL_INVALID = "L'adresse email n'est pas valide";
    public static final String PHONE_INVALID = "Le numéro de téléphone n'est pas valide";
    public static final String ACCOUNT_NUMBER_INVALID = "Le numéro de compte n'est pas valide";
    public static final String PASSWORD_TOO_SHORT = "Le mot de passe doit contenir au moins " + MIN_PASSWORD_LENGTH + " caractères";
    public static final String NAME_INVALID = "Le nom contient des caractères invalides";
    public static final String BALANCE_INVALID = "Le solde doit être positif et inférieur à " + MAX_BALANCE + " FCFA";
    public static final String TRANSACTION_AMOUNT_INVALID = "Le montant de la transaction doit être entre " + MIN_TRANSACTION_AMOUNT + " et " + MAX_TRANSACTION_AMOUNT + " FCFA";
    public static final String ACCOUNT_TYPE_INVALID = "Le type de compte doit être CHEQUE ou EPARGNE";
    public static final String CURRENCY_INVALID = "La devise doit être FCFA";
    public static final String FIELD_REQUIRED = "Ce champ est obligatoire";
    public static final String LENGTH_INVALID = "La longueur du champ est invalide";
}