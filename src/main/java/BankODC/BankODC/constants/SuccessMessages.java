package BankODC.BankODC.constants;



public enum SuccessMessages {

    OPERATION_SUCCESSFUL("success.operation_successful"),
    DATA_RETRIEVED_SUCCESSFULLY("success.data_retrieved_successfully"),
    DATA_SAVED_SUCCESSFULLY("success.data_saved_successfully"),
    DATA_UPDATED_SUCCESSFULLY("success.data_updated_successfully"),
    DATA_DELETED_SUCCESSFULLY("success.data_deleted_successfully"),

    CLIENT_CREATED_SUCCESSFULLY("success.client_created_successfully"),
    CLIENT_UPDATED_SUCCESSFULLY("success.client_updated_successfully"),
    CLIENT_DELETED_SUCCESSFULLY("success.client_deleted_successfully"),
    CLIENT_RETRIEVED_SUCCESSFULLY("success.client_retrieved_successfully"),
    CLIENTS_RETRIEVED_SUCCESSFULLY("success.clients_retrieved_successfully"),

    COMPTE_CREATED_SUCCESSFULLY("success.compte_created_successfully"),
    COMPTE_UPDATED_SUCCESSFULLY("success.compte_updated_successfully"),
    COMPTE_DELETED_SUCCESSFULLY("success.compte_deleted_successfully"),
    COMPTE_RETRIEVED_SUCCESSFULLY("success.compte_retrieved_successfully"),
    COMPTES_RETRIEVED_SUCCESSFULLY("success.comptes_retrieved_successfully"),
    COMPTE_BALANCE_UPDATED("success.compte_balance_updated"),

    TRANSACTION_CREATED_SUCCESSFULLY("success.transaction_created_successfully"),
    TRANSACTION_UPDATED_SUCCESSFULLY("success.transaction_updated_successfully"),
    TRANSACTION_DELETED_SUCCESSFULLY("success.transaction_deleted_successfully"),
    TRANSACTION_RETRIEVED_SUCCESSFULLY("success.transaction_retrieved_successfully"),
    TRANSACTIONS_RETRIEVED_SUCCESSFULLY("success.transactions_retrieved_successfully"),
    TRANSACTION_PROCESSED_SUCCESSFULLY("success.transaction_processed_successfully"),

    ADMIN_CREATED_SUCCESSFULLY("success.admin_created_successfully"),
    ADMIN_UPDATED_SUCCESSFULLY("success.admin_updated_successfully"),
    ADMIN_DELETED_SUCCESSFULLY("success.admin_deleted_successfully"),
    ADMIN_RETRIEVED_SUCCESSFULLY("success.admin_retrieved_successfully"),
    ADMINS_RETRIEVED_SUCCESSFULLY("success.admins_retrieved_successfully"),
    ADMIN_AUTHENTICATED_SUCCESSFULLY("success.admin_authenticated_successfully"),
    LOGIN_SUCCESSFUL("fallou"),

    DATABASE_CONNECTION_SUCCESSFUL("success.database_connection_successful"),
    SYSTEM_INITIALIZED_SUCCESSFULLY("success.system_initialized_successfully"),
    BACKUP_COMPLETED_SUCCESSFULLY("success.backup_completed_successfully"),
    CONFIGURATION_LOADED_SUCCESSFULLY("success.configuration_loaded_successfully");

    private final String messageKey;

    SuccessMessages(String messageKey) {
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