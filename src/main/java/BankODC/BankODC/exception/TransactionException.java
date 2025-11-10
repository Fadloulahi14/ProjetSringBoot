package BankODC.BankODC.exception;

public class TransactionException extends RuntimeException {

    private String errorCode;

    public TransactionException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public TransactionException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}