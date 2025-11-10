package BankODC.BankODC.constants;

public enum ErrorMessages {

    OPERATION_FAILED("error.operation_failed"),
    INVALID_DATA("error.invalid_data"),
    RESOURCE_NOT_FOUND("error.resource_not_found"),
    UNAUTHORIZED_ACCESS("error.unauthorized_access"),
    INTERNAL_SERVER_ERROR("error.internal_server_error"),

    CLIENT_NOT_FOUND("error.client_not_found"),
    CLIENT_ALREADY_EXISTS("error.client_already_exists"),
    CLIENT_INVALID_DATA("error.client_invalid_data"),
    CLIENT_CREATION_FAILED("error.client_creation_failed"),
    CLIENT_UPDATE_FAILED("error.client_update_failed"),
    CLIENT_DELETION_FAILED("error.client_deletion_failed"),

    COMPTE_NOT_FOUND("error.compte_not_found"),
    COMPTE_ALREADY_EXISTS("error.compte_already_exists"),
    COMPTE_INSUFFICIENT_BALANCE("error.compte_insufficient_balance"),
    COMPTE_NEGATIVE_BALANCE("error.compte_negative_balance"),
    COMPTE_INVALID_TYPE("error.compte_invalid_type"),
    COMPTE_CREATION_FAILED("error.compte_creation_failed"),
    COMPTE_UPDATE_FAILED("error.compte_update_failed"),
    COMPTE_DELETION_FAILED("error.compte_deletion_failed"),
    COMPTE_CANNOT_DELETE_WITH_BALANCE("error.compte_cannot_delete_with_balance"),

    TRANSACTION_NOT_FOUND("error.transaction_not_found"),
    TRANSACTION_INVALID_AMOUNT("error.transaction_invalid_amount"),
    TRANSACTION_INSUFFICIENT_FUNDS("error.transaction_insufficient_funds"),
    TRANSACTION_CREATION_FAILED("error.transaction_creation_failed"),
    TRANSACTION_UPDATE_FAILED("error.transaction_update_failed"),
    TRANSACTION_DELETION_FAILED("error.transaction_deletion_failed"),

    ADMIN_NOT_FOUND("error.admin_not_found"),
    ADMIN_ALREADY_EXISTS("error.admin_already_exists"),
    ADMIN_INVALID_CREDENTIALS("error.admin_invalid_credentials"),
    ADMIN_CREATION_FAILED("error.admin_creation_failed"),
    ADMIN_UPDATE_FAILED("error.admin_update_failed"),
    ADMIN_DELETION_FAILED("error.admin_deletion_failed"),

    VALIDATION_FAILED("error.validation_failed"),
    REQUIRED_FIELD_MISSING("error.required_field_missing"),
    INVALID_EMAIL_FORMAT("error.invalid_email_format"),
    INVALID_PHONE_FORMAT("error.invalid_phone_format"),
    PASSWORD_TOO_SHORT("error.password_too_short"),
    INVALID_DATE_FORMAT("error.invalid_date_format"),

    DATABASE_CONNECTION_ERROR("error.database_connection_error"),
    DATABASE_QUERY_ERROR("error.database_query_error"),
    DATABASE_CONSTRAINT_VIOLATION("error.database_constraint_violation");

    private final String messageKey;

    ErrorMessages(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getMessage() {
        // Cette méthode retournera la clé pour l'instant
        // Le MessageUtil sera utilisé dans les services pour récupérer le message traduit
        return messageKey;
    }

    @Override
    public String toString() {
        return messageKey;
    }
}