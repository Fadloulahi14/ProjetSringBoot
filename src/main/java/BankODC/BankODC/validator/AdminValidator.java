package BankODC.BankODC.validator;

import BankODC.BankODC.dto.AdminCreateDTO;
import BankODC.BankODC.dto.request.AdminRequest;
import BankODC.BankODC.entity.Admin;
import BankODC.BankODC.exception.AdminException;

public class AdminValidator {

    public static void validateAdminCreateDTO(AdminCreateDTO dto) {
        if (dto == null) {
            throw new AdminException("INVALID_DATA", "Les données d'admin sont nulles");
        }

        if (!ValidationRules.isValidName(dto.getNom())) {
            throw new AdminException("INVALID_NAME", ValidationRules.NAME_INVALID);
        }

        if (dto.getPrenom() != null && !ValidationRules.isValidName(dto.getPrenom())) {
            throw new AdminException("INVALID_FIRST_NAME", "Le prénom contient des caractères invalides");
        }

        if (!ValidationRules.isValidEmail(dto.getEmail())) {
            throw new AdminException("INVALID_EMAIL", ValidationRules.EMAIL_INVALID);
        }

        if (dto.getTelephone() != null && !ValidationRules.isValidPhone(dto.getTelephone())) {
            throw new AdminException("INVALID_PHONE", ValidationRules.PHONE_INVALID);
        }

        if (!ValidationRules.isValidPassword(dto.getPassword())) {
            throw new AdminException("INVALID_PASSWORD", ValidationRules.PASSWORD_TOO_SHORT);
        }
    }

    public static void validateAdminRequest(AdminRequest request) {
        if (request == null) {
            throw new AdminException("INVALID_DATA", "Les données de requête admin sont nulles");
        }

        if (!ValidationRules.isValidName(request.getNom())) {
            throw new AdminException("INVALID_NAME", ValidationRules.NAME_INVALID);
        }

        if (request.getPrenom() != null && !ValidationRules.isValidName(request.getPrenom())) {
            throw new AdminException("INVALID_FIRST_NAME", "Le prénom contient des caractères invalides");
        }

        if (!ValidationRules.isValidEmail(request.getEmail())) {
            throw new AdminException("INVALID_EMAIL", ValidationRules.EMAIL_INVALID);
        }

        if (request.getTelephone() != null && !ValidationRules.isValidPhone(request.getTelephone())) {
            throw new AdminException("INVALID_PHONE", ValidationRules.PHONE_INVALID);
        }
    }

    public static void validateAdminEntity(Admin admin) {
        if (admin == null) {
            throw new AdminException("INVALID_DATA", "L'entité admin est nulle");
        }

        if (!ValidationRules.isValidName(admin.getNom())) {
            throw new AdminException("INVALID_NAME", ValidationRules.NAME_INVALID);
        }

        if (admin.getPrenom() != null && !ValidationRules.isValidName(admin.getPrenom())) {
            throw new AdminException("INVALID_FIRST_NAME", "Le prénom contient des caractères invalides");
        }

        if (!ValidationRules.isValidEmail(admin.getEmail())) {
            throw new AdminException("INVALID_EMAIL", ValidationRules.EMAIL_INVALID);
        }

        if (admin.getTelephone() != null && !ValidationRules.isValidPhone(admin.getTelephone())) {
            throw new AdminException("INVALID_PHONE", ValidationRules.PHONE_INVALID);
        }

        if (admin.getPassword() != null && !ValidationRules.isValidPassword(admin.getPassword())) {
            throw new AdminException("INVALID_PASSWORD", ValidationRules.PASSWORD_TOO_SHORT);
        }
    }
}