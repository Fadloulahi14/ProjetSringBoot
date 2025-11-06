package BankODC.BankODC.constants;

public enum SuccessMessages {


    OPERATION_SUCCESSFUL("Opération réalisée avec succès"),
    DATA_RETRIEVED_SUCCESSFULLY("Données récupérées avec succès"),
    DATA_SAVED_SUCCESSFULLY("Données sauvegardées avec succès"),
    DATA_UPDATED_SUCCESSFULLY("Données mises à jour avec succès"),
    DATA_DELETED_SUCCESSFULLY("Données supprimées avec succès"),


    CLIENT_CREATED_SUCCESSFULLY("Client créé avec succès"),
    CLIENT_UPDATED_SUCCESSFULLY("Client mis à jour avec succès"),
    CLIENT_DELETED_SUCCESSFULLY("Client supprimé avec succès"),
    CLIENT_RETRIEVED_SUCCESSFULLY("Client récupéré avec succès"),
    CLIENTS_RETRIEVED_SUCCESSFULLY("Liste des clients récupérée avec succès"),


    COMPTE_CREATED_SUCCESSFULLY("Compte créé avec succès"),
    COMPTE_UPDATED_SUCCESSFULLY("Compte mis à jour avec succès"),
    COMPTE_DELETED_SUCCESSFULLY("Compte supprimé avec succès"),
    COMPTE_RETRIEVED_SUCCESSFULLY("Compte récupéré avec succès"),
    COMPTES_RETRIEVED_SUCCESSFULLY("Liste des comptes récupérée avec succès"),
    COMPTE_BALANCE_UPDATED("Solde du compte mis à jour avec succès"),


    TRANSACTION_CREATED_SUCCESSFULLY("Transaction créée avec succès"),
    TRANSACTION_UPDATED_SUCCESSFULLY("Transaction mise à jour avec succès"),
    TRANSACTION_DELETED_SUCCESSFULLY("Transaction supprimée avec succès"),
    TRANSACTION_RETRIEVED_SUCCESSFULLY("Transaction récupérée avec succès"),
    TRANSACTIONS_RETRIEVED_SUCCESSFULLY("Liste des transactions récupérée avec succès"),
    TRANSACTION_PROCESSED_SUCCESSFULLY("Transaction traitée avec succès"),


    ADMIN_CREATED_SUCCESSFULLY("Administrateur créé avec succès"),
    ADMIN_UPDATED_SUCCESSFULLY("Administrateur mis à jour avec succès"),
    ADMIN_DELETED_SUCCESSFULLY("Administrateur supprimé avec succès"),
    ADMIN_RETRIEVED_SUCCESSFULLY("Administrateur récupéré avec succès"),
    ADMINS_RETRIEVED_SUCCESSFULLY("Liste des administrateurs récupérée avec succès"),
    ADMIN_AUTHENTICATED_SUCCESSFULLY("Administrateur authentifié avec succès"),


    DATABASE_CONNECTION_SUCCESSFUL("Connexion à la base de données établie"),
    SYSTEM_INITIALIZED_SUCCESSFULLY("Système initialisé avec succès"),
    BACKUP_COMPLETED_SUCCESSFULLY("Sauvegarde terminée avec succès"),
    CONFIGURATION_LOADED_SUCCESSFULLY("Configuration chargée avec succès");

    private final String message;

    SuccessMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}