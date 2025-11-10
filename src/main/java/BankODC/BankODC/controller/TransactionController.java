package BankODC.BankODC.controller;

import BankODC.BankODC.constants.ApiResponse;
import BankODC.BankODC.dto.request.TransactionRequest;
import BankODC.BankODC.dto.response.TransactionResponse;
import BankODC.BankODC.service.interfaces.TransactionService;
import BankODC.BankODC.util.MessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "API de gestion des transactions bancaires")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MessageUtil messageUtil;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtenir toutes les transactions", description = "Récupère la liste de toutes les transactions")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getAllTransactions() {
        List<TransactionResponse> transactions = transactionService.getAllTransactions().stream()
            .map(t -> new TransactionResponse(t.getId(), t.getCompteId(), t.getType(), t.getMontant(), t.getDate()))
            .toList();
        String successMessage = messageUtil.getSuccessMessage("transactions_retrieved_successfully");
        return ResponseEntity.ok(ApiResponse.success(successMessage, transactions));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Obtenir une transaction par ID", description = "Récupère une transaction spécifique par son identifiant")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransactionById(@PathVariable UUID id) {
        Optional<TransactionResponse> transaction = transactionService.getTransactionById(id);
        if (transaction.isPresent()) {
            String successMessage = messageUtil.getSuccessMessage("transaction_retrieved_successfully");
            return ResponseEntity.ok(ApiResponse.success(successMessage, transaction.get()));
        } else {
            String errorMessage = messageUtil.getErrorMessage("transaction_not_found");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer une nouvelle transaction", description = "Ajoute une nouvelle transaction au système")
    public ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        try {
            TransactionResponse savedTransaction = transactionService.saveTransactionFromRequest(transactionRequest);
            String successMessage = messageUtil.getSuccessMessage("transaction_created_successfully");
            return ResponseEntity.ok(ApiResponse.success(successMessage, savedTransaction));
        } catch (Exception e) {
            String errorMessage = messageUtil.getErrorMessage("transaction_creation_failed");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour une transaction", description = "Modifie les informations d'une transaction existante")
    public ResponseEntity<ApiResponse<TransactionResponse>> updateTransaction(@PathVariable UUID id, @Valid @RequestBody TransactionRequest transactionRequest) {
        try {
            TransactionResponse updatedTransaction = transactionService.updateTransactionFromRequest(id, transactionRequest);
            String successMessage = messageUtil.getSuccessMessage("transaction_updated_successfully");
            return ResponseEntity.ok(ApiResponse.success(successMessage, updatedTransaction));
        } catch (Exception e) {
            String errorMessage = messageUtil.getErrorMessage("transaction_not_found");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une transaction", description = "Supprime une transaction du système")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(@PathVariable UUID id) {
        Optional<TransactionResponse> transaction = transactionService.getTransactionById(id);
        if (transaction.isPresent()) {
            transactionService.deleteTransaction(id);
            String successMessage = messageUtil.getSuccessMessage("transaction_deleted_successfully");
            return ResponseEntity.ok(ApiResponse.success(successMessage, null));
        } else {
            String errorMessage = messageUtil.getErrorMessage("transaction_not_found");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }
}