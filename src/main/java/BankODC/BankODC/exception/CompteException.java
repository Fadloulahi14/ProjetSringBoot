package BankODC.BankODC.exception;

public class CompteException extends RuntimeException {
    private final String code;

    public CompteException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}