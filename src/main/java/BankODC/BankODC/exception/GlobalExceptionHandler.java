package BankODC.BankODC.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global Exception Handler pour intercepter toutes les exceptions de l'application
 * et retourner des réponses cohérentes au format ApiResponse
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestionnaire pour les violations de contraintes de données (clés étrangères, unicité, etc.)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        System.out.println("=== DATA INTEGRITY VIOLATION EXCEPTION HANDLER ===");
        System.out.println("Exception reçue: " + ex.getClass().getSimpleName());
        System.out.println("Message: " + ex.getMessage());
        System.out.println("Cause: " + (ex.getCause() != null ? ex.getCause().getClass().getSimpleName() + ": " + ex.getCause().getMessage() : "null"));
        ex.printStackTrace();
        System.out.println("=== FIN DATA INTEGRITY VIOLATION EXCEPTION HANDLER ===");

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("data", null);
        body.put("timestamp", LocalDateTime.now());
        body.put("errorCode", "DATA_INTEGRITY_ERROR");

        String message = ex.getMessage();
        if (message.contains("uk6dotkott2kjsp8vw4d0m25fb7")) {
            body.put("message", "Un utilisateur avec cet email existe déjà");
        } else if (message.contains("foreign key")) {
            body.put("message", "Violation de clé étrangère");
        } else if (message.contains("unique constraint")) {
            body.put("message", "Violation de contrainte d'unicité");
        } else {
            body.put("message", "Erreur de contrainte de données: " + ex.getMostSpecificCause().getMessage());
        }

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gestionnaire pour les violations de contraintes de validation Bean (JSR-303)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        System.out.println("=== CONSTRAINT VIOLATION EXCEPTION HANDLER ===");
        System.out.println("Exception reçue: " + ex.getClass().getSimpleName());
        System.out.println("Message: " + ex.getMessage());
        ex.printStackTrace();
        System.out.println("=== FIN CONSTRAINT VIOLATION EXCEPTION HANDLER ===");

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("data", null);
        body.put("timestamp", LocalDateTime.now());
        body.put("errorCode", "VALIDATION_ERROR");

        String violations = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        body.put("message", "Erreurs de validation: " + violations);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gestionnaire pour les erreurs de validation des arguments de méthode (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        System.out.println("=== METHOD ARGUMENT NOT VALID EXCEPTION HANDLER ===");
        System.out.println("Exception reçue: " + ex.getClass().getSimpleName());
        System.out.println("Message: " + ex.getMessage());
        ex.printStackTrace();
        System.out.println("=== FIN METHOD ARGUMENT NOT VALID EXCEPTION HANDLER ===");

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("data", null);
        body.put("timestamp", LocalDateTime.now());
        body.put("errorCode", "VALIDATION_ERROR");

        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        body.put("message", "Erreurs de validation: " + errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gestionnaire pour les erreurs de type d'argument de méthode
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        System.out.println("=== METHOD ARGUMENT TYPE MISMATCH EXCEPTION HANDLER ===");
        System.out.println("Exception reçue: " + ex.getClass().getSimpleName());
        System.out.println("Message: " + ex.getMessage());
        ex.printStackTrace();
        System.out.println("=== FIN METHOD ARGUMENT TYPE MISMATCH EXCEPTION HANDLER ===");

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("data", null);
        body.put("timestamp", LocalDateTime.now());
        body.put("errorCode", "TYPE_MISMATCH_ERROR");

        body.put("message", "Type d'argument invalide pour le paramètre '" + ex.getName() + "': attendu " +
                ex.getRequiredType().getSimpleName() + ", reçu " + ex.getValue());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

   
    @ExceptionHandler(PersistenceException.class)
    @ResponseBody
    public ResponseEntity<Object> handlePersistenceException(PersistenceException ex, WebRequest request) {
        System.out.println("=== PERSISTENCE EXCEPTION HANDLER ===");
        System.out.println("Exception reçue: " + ex.getClass().getSimpleName());
        System.out.println("Message: " + ex.getMessage());
        System.out.println("Cause: " + (ex.getCause() != null ? ex.getCause().getClass().getSimpleName() + ": " + ex.getCause().getMessage() : "null"));
        ex.printStackTrace();
        System.out.println("=== FIN PERSISTENCE EXCEPTION HANDLER ===");

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("data", null);
        body.put("timestamp", LocalDateTime.now());
        body.put("errorCode", "PERSISTENCE_ERROR");

        body.put("message", "Erreur de persistance: " + (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage()));

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

 
    @ExceptionHandler(org.springframework.transaction.TransactionSystemException.class)
    @ResponseBody
    public ResponseEntity<Object> handleTransactionSystemException(org.springframework.transaction.TransactionSystemException ex, WebRequest request) {
        System.out.println("=== TRANSACTION SYSTEM EXCEPTION HANDLER ===");
        System.out.println("Exception reçue: " + ex.getClass().getSimpleName());
        System.out.println("Message: " + ex.getMessage());
        System.out.println("Cause: " + (ex.getCause() != null ? ex.getCause().getClass().getSimpleName() + ": " + ex.getCause().getMessage() : "null"));
        ex.printStackTrace();
        System.out.println("=== FIN TRANSACTION SYSTEM EXCEPTION HANDLER ===");

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("data", null);
        body.put("timestamp", LocalDateTime.now());
        body.put("errorCode", "TRANSACTION_ERROR");

        body.put("message", "Erreur de transaction: " + (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage()));

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Gestionnaire pour les exceptions Compte personnalisées
     */
    @ExceptionHandler(CompteException.class)
    @ResponseBody
    public ResponseEntity<Object> handleCompteException(CompteException ex, WebRequest request) {
        System.out.println("=== COMPTE EXCEPTION HANDLER ===");
        System.out.println("Exception reçue: " + ex.getClass().getSimpleName());
        System.out.println("Code: " + ex.getCode());
        System.out.println("Message: " + ex.getMessage());
        ex.printStackTrace();
        System.out.println("=== FIN COMPTE EXCEPTION HANDLER ===");

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("data", null);
        body.put("timestamp", LocalDateTime.now());
        body.put("errorCode", ex.getCode());

        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gestionnaire catch-all pour toutes les autres exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        System.out.println("=== GLOBAL EXCEPTION HANDLER - ALL EXCEPTIONS ===");
        System.out.println("Exception reçue: " + ex.getClass().getSimpleName());
        System.out.println("Message: " + ex.getMessage());
        System.out.println("Cause: " + (ex.getCause() != null ? ex.getCause().getClass().getSimpleName() + ": " + ex.getCause().getMessage() : "null"));
        ex.printStackTrace();
        System.out.println("=== FIN GLOBAL EXCEPTION HANDLER ===");

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("message", "Erreur interne du serveur: " + ex.getMessage());
        body.put("data", null);
        body.put("timestamp", LocalDateTime.now());
        body.put("errorCode", "INTERNAL_ERROR");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}