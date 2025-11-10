package BankODC.BankODC.validator;

import BankODC.BankODC.dto.request.ClientRequest;
import BankODC.BankODC.entity.Client;
import BankODC.BankODC.exception.ClientException;

public class ClientValidator {

    public static void validateClientRequest(ClientRequest request) {
        if (request == null) {
            throw new ClientException("INVALID_DATA", "Les données du client sont nulles");
        }

        if (!ValidationRules.isValidName(request.getNom())) {
            throw new ClientException("INVALID_NAME", ValidationRules.NAME_INVALID);
        }

        if (request.getPrenom() != null && !ValidationRules.isValidName(request.getPrenom())) {
            throw new ClientException("INVALID_FIRST_NAME", "Le prénom contient des caractères invalides");
        }

        if (!ValidationRules.isValidEmail(request.getEmail())) {
            throw new ClientException("INVALID_EMAIL", ValidationRules.EMAIL_INVALID);
        }

        if (request.getTelephone() != null && !ValidationRules.isValidPhone(request.getTelephone())) {
            throw new ClientException("INVALID_PHONE", ValidationRules.PHONE_INVALID);
        }

        if (request.getAdresse() != null && !ValidationRules.isValidLength(request.getAdresse(), 5, 255)) {
            throw new ClientException("INVALID_ADDRESS", "L'adresse doit contenir entre 5 et 255 caractères");
        }
    }

    public static void validateClientEntity(Client client) {
        if (client == null) {
            throw new ClientException("INVALID_DATA", "L'entité client est nulle");
        }

        if (!ValidationRules.isValidName(client.getNom())) {
            throw new ClientException("INVALID_NAME", ValidationRules.NAME_INVALID);
        }

        if (client.getPrenom() != null && !ValidationRules.isValidName(client.getPrenom())) {
            throw new ClientException("INVALID_FIRST_NAME", "Le prénom contient des caractères invalides");
        }

        if (!ValidationRules.isValidEmail(client.getEmail())) {
            throw new ClientException("INVALID_EMAIL", ValidationRules.EMAIL_INVALID);
        }

        if (client.getTelephone() != null && !ValidationRules.isValidPhone(client.getTelephone())) {
            throw new ClientException("INVALID_PHONE", ValidationRules.PHONE_INVALID);
        }

        if (client.getAdresse() != null && !ValidationRules.isValidLength(client.getAdresse(), 5, 255)) {
            throw new ClientException("INVALID_ADDRESS", "L'adresse doit contenir entre 5 et 255 caractères");
        }
    }
}