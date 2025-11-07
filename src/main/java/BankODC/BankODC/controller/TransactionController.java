package BankODC.BankODC.controller;

import BankODC.BankODC.dto.TransactionDTO;
import BankODC.BankODC.service.ITransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private ITransactionService transactionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtenir toutes les transactions", description = "Récupère la liste de toutes les transactions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des transactions récupérée avec succès")
    })
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Obtenir une transaction par ID", description = "Récupère une transaction spécifique par son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transaction trouvée"),
        @ApiResponse(responseCode = "404", description = "Transaction non trouvée")
    })
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable UUID id) {
        Optional<TransactionDTO> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer une nouvelle transaction", description = "Ajoute une nouvelle transaction au système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transaction créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public TransactionDTO createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        return transactionService.saveTransaction(transactionDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour une transaction", description = "Modifie les informations d'une transaction existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transaction mise à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Transaction non trouvée")
    })
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable UUID id, @Valid @RequestBody TransactionDTO transactionDetails) {
        Optional<TransactionDTO> existingTransaction = transactionService.getTransactionById(id);
        if (existingTransaction.isPresent()) {
            transactionDetails.setId(id);
            TransactionDTO updatedTransaction = transactionService.saveTransaction(transactionDetails);
            return ResponseEntity.ok(updatedTransaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une transaction", description = "Supprime une transaction du système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Transaction supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Transaction non trouvée")
    })
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        Optional<TransactionDTO> transaction = transactionService.getTransactionById(id);
        if (transaction.isPresent()) {
            transactionService.deleteTransaction(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}