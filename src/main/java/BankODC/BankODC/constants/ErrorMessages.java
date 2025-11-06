package BankODC.BankODC.constants;

public enum ErrorMessages {


    OPERATION_FAILED("L'opération a échoué"),
    INVALID_DATA("Données invalides"),
    RESOURCE_NOT_FOUND("Ressource non trouvée"),
    UNAUTHORIZED_ACCESS("Accès non autorisé"),
    INTERNAL_SERVER_ERROR("Erreur interne du serveur"),


    CLIENT_NOT_FOUND("Client non trouvé"),
    CLIENT_ALREADY_EXISTS("Client déjà existant"),
    CLIENT_INVALID_DATA("Données du client invalides"),
    CLIENT_CREATION_FAILED("Échec de la création du client"),
    CLIENT_UPDATE_FAILED("Échec de la mise à jour du client"),
    CLIENT_DELETION_FAILED("Échec de la suppression du client"),


    COMPTE_NOT_FOUND("Compte non trouvé"),
    COMPTE_ALREADY_EXISTS("Compte déjà existant"),
    COMPTE_INSUFFICIENT_BALANCE("Solde insuffisant"),
    COMPTE_NEGATIVE_BALANCE("Le solde ne peut pas être négatif"),
    COMPTE_INVALID_TYPE("Type de compte invalide"),
    COMPTE_CREATION_FAILED("Échec de la création du compte"),
    COMPTE_UPDATE_FAILED("Échec de la mise à jour du compte"),
    COMPTE_DELETION_FAILED("Échec de la suppression du compte"),
    COMPTE_CANNOT_DELETE_WITH_BALANCE("Impossible de supprimer un compte avec un solde positif"),


    TRANSACTION_NOT_FOUND("Transaction non trouvée"),
    TRANSACTION_INVALID_AMOUNT("Montant de transaction invalide"),
    TRANSACTION_INSUFFICIENT_FUNDS("Fonds insuffisants pour la transaction"),
    TRANSACTION_CREATION_FAILED("Échec de la création de la transaction"),
    TRANSACTION_UPDATE_FAILED("Échec de la mise à jour de la transaction"),
    TRANSACTION_DELETION_FAILED("Échec de la suppression de la transaction"),


    ADMIN_NOT_FOUND("Administrateur non trouvé"),
    ADMIN_ALREADY_EXISTS("Administrateur déjà existant"),
    ADMIN_INVALID_CREDENTIALS("Identifiants invalides"),
    ADMIN_CREATION_FAILED("Échec de la création de l'administrateur"),
    ADMIN_UPDATE_FAILED("Échec de la mise à jour de l'administrateur"),
    ADMIN_DELETION_FAILED("Échec de la suppression de l'administrateur"),


    VALIDATION_FAILED("Échec de la validation"),
    REQUIRED_FIELD_MISSING("Champ obligatoire manquant"),
    INVALID_EMAIL_FORMAT("Format d'email invalide"),
    INVALID_PHONE_FORMAT("Format de téléphone invalide"),
    PASSWORD_TOO_SHORT("Mot de passe trop court"),
    INVALID_DATE_FORMAT("Format de date invalide"),


    DATABASE_CONNECTION_ERROR("Erreur de connexion à la base de données"),
    DATABASE_QUERY_ERROR("Erreur lors de l'exécution de la requête"),
    DATABASE_CONSTRAINT_VIOLATION("Violation de contrainte de base de données");

    private final String message;

    ErrorMessages(String message) {
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