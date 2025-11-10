package BankODC.BankODC.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtil {

    private final MessageSource messageSource;

    @Autowired
    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Récupère un message en utilisant la locale actuelle
     * @param key Clé du message
     * @return Message traduit
     */
    public String getMessage(String key) {
        return getMessage(key, null, LocaleContextHolder.getLocale());
    }

    /**
     * Récupère un message avec des arguments en utilisant la locale actuelle
     * @param key Clé du message
     * @param args Arguments à substituer dans le message
     * @return Message traduit avec arguments
     */
    public String getMessage(String key, Object[] args) {
        return getMessage(key, args, LocaleContextHolder.getLocale());
    }

    /**
     * Récupère un message pour une locale spécifique
     * @param key Clé du message
     * @param locale Locale souhaitée
     * @return Message traduit
     */
    public String getMessage(String key, Locale locale) {
        return getMessage(key, null, locale);
    }

    /**
     * Récupère un message avec des arguments pour une locale spécifique
     * @param key Clé du message
     * @param args Arguments à substituer dans le message
     * @param locale Locale souhaitée
     * @return Message traduit avec arguments
     */
    public String getMessage(String key, Object[] args, Locale locale) {
        try {
            String message = messageSource.getMessage(key, args, locale);
            System.out.println("MessageUtil - Key: " + key + ", Locale: " + locale + ", Message: " + message);
            return message;
        } catch (Exception e) {
            // Retourne la clé si le message n'est pas trouvé
            System.out.println("MessageUtil - Key not found: " + key + ", Locale: " + locale + ", Exception: " + e.getMessage());
            // Essayer avec la locale par défaut si la locale actuelle ne fonctionne pas
            try {
                Locale defaultLocale = Locale.FRENCH; // ou Locale.ENGLISH selon votre préférence
                String defaultMessage = messageSource.getMessage(key, args, defaultLocale);
                System.out.println("MessageUtil - Using default locale, Message: " + defaultMessage);
                return defaultMessage;
            } catch (Exception e2) {
                System.out.println("MessageUtil - Default locale also failed: " + e2.getMessage());
                // Essayer avec toutes les locales disponibles
                try {
                    String fallbackMessage = messageSource.getMessage(key, args, Locale.getDefault());
                    System.out.println("MessageUtil - Using system default locale, Message: " + fallbackMessage);
                    return fallbackMessage;
                } catch (Exception e3) {
                    System.out.println("MessageUtil - All locales failed, returning key: " + key);
                    return key;
                }
            }
        }
    }

    /**
     * Test method to check if messages are loaded correctly
     */
    public void testMessages() {
        System.out.println("=== TESTING MESSAGE LOADING ===");
        String[] testKeys = {
            "error.admin_invalid_credentials",
            "success.login_successful",
            "error.client_not_found",
            "success.client_created_successfully"
        };

        for (String key : testKeys) {
            try {
                String message = getMessage(key);
                System.out.println("Key: " + key + " -> Message: " + message);
            } catch (Exception e) {
                System.out.println("Key: " + key + " -> ERROR: " + e.getMessage());
            }
        }
        System.out.println("=== END TESTING ===");
    }

    /**
     * Récupère un message d'erreur
     * @param errorKey Clé du message d'erreur (sans le préfixe "error.")
     * @return Message d'erreur traduit
     */
    public String getErrorMessage(String errorKey) {
        return getMessage("error." + errorKey);
    }

    /**
     * Récupère un message d'erreur avec arguments
     * @param errorKey Clé du message d'erreur (sans le préfixe "error.")
     * @param args Arguments à substituer
     * @return Message d'erreur traduit avec arguments
     */
    public String getErrorMessage(String errorKey, Object[] args) {
        return getMessage("error." + errorKey, args);
    }

    /**
     * Récupère un message de succès
     * @param successKey Clé du message de succès (sans le préfixe "success.")
     * @return Message de succès traduit
     */
    public String getSuccessMessage(String successKey) {
        return getMessage("success." + successKey);
    }

    /**
     * Récupère un message de succès avec arguments
     * @param successKey Clé du message de succès (sans le préfixe "success.")
     * @param args Arguments à substituer
     * @return Message de succès traduit avec arguments
     */
    public String getSuccessMessage(String successKey, Object[] args) {
        return getMessage("success." + successKey, args);
    }
}