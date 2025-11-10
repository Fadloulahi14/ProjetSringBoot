package BankODC.BankODC.exception;

public class ClientException extends RuntimeException {

    private String errorCode;

    public ClientException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ClientException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}