package BankODC.BankODC.controller;

import BankODC.BankODC.constants.ApiResponse;
import BankODC.BankODC.constants.ErrorMessages;
import BankODC.BankODC.constants.SuccessMessages;
import BankODC.BankODC.dto.CompteCreateDTO;
import BankODC.BankODC.dto.BalanceUpdateDTO;
import BankODC.BankODC.dto.CompteBlockDTO;
import BankODC.BankODC.dto.CompteDTO;
import BankODC.BankODC.dto.PaginationResponse;
import BankODC.BankODC.dto.TransactionDTO;
import BankODC.BankODC.service.ICompteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comptes")
@Tag(name = "Comptes", description = "API de gestion des comptes bancaires")
public class CompteController {

    @Autowired
    private ICompteService compteService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Obtenir les comptes avec pagination et filtrage", description = "Récupère la liste paginée des comptes bancaires avec possibilité de filtrage par type et utilisateur")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Liste des comptes récupérée avec succès")
    })
    public ResponseEntity<ApiResponse<PaginationResponse<CompteDTO>>> getComptesPaginated(
            @Parameter(description = "Numéro de page (commence à 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Champ de tri") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Direction de tri (asc/desc)") @RequestParam(defaultValue = "asc") String sortDir,
            @Parameter(description = "Type de compte pour filtrer") @RequestParam(required = false) String type,
            @Parameter(description = "ID utilisateur pour filtrer") @RequestParam(required = false) UUID userid) {
        Pageable pageable = PageRequest.of(page, size, org.springframework.data.domain.Sort.by(
                sortDir.equalsIgnoreCase("desc") ? org.springframework.data.domain.Sort.Direction.DESC :
                org.springframework.data.domain.Sort.Direction.ASC, sortBy));
        PaginationResponse<CompteDTO> comptes = compteService.getComptesPaginated(pageable, type, userid);
        return ResponseEntity.ok(ApiResponse.success(SuccessMessages.COMPTES_RETRIEVED_SUCCESSFULLY, comptes));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Obtenir un compte par ID", description = "Récupère un compte bancaire spécifique par son identifiant")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte trouvé"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<ApiResponse<CompteDTO>> getCompteById(@PathVariable UUID id) {
        return compteService.getCompteById(id)
                .map(compte -> ResponseEntity.ok(ApiResponse.success(SuccessMessages.COMPTE_RETRIEVED_SUCCESSFULLY, compte)))
                .orElse(ResponseEntity.ok(ApiResponse.error(ErrorMessages.COMPTE_NOT_FOUND, null)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer un nouveau compte avec client", description = "Crée un compte bancaire avec un nouveau client ou utilise un client existant")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte créé avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<ApiResponse<CompteDTO>> createCompte(@RequestBody CompteCreateDTO compteCreateDTO) {
        System.out.println("=== CONTRÔLEUR CRÉATION COMPTE ===");
        System.out.println("Données reçues: " + compteCreateDTO);
        try {
            CompteDTO savedCompte = compteService.createCompteWithClient(compteCreateDTO);
            System.out.println("=== COMPTE CRÉÉ AVEC SUCCÈS ===");
            return ResponseEntity.ok(ApiResponse.success(SuccessMessages.COMPTE_CREATED_SUCCESSFULLY, savedCompte));
        } catch (Exception e) {
            System.out.println("=== CONTRÔLEUR EXCEPTION ===");
            System.out.println("Type d'exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
            System.out.println("Cause: " + (e.getCause() != null ? e.getCause().getClass().getSimpleName() + ": " + e.getCause().getMessage() : "null"));
            System.out.println("Stack trace:");
            e.printStackTrace();
            System.out.println("=== FIN CONTRÔLEUR EXCEPTION ===");
            // Return the actual error message instead of generic INVALID_DATA
            return ResponseEntity.ok(ApiResponse.error(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour un compte", description = "Modifie les informations d'un compte bancaire existant")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte mis à jour avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<ApiResponse<CompteDTO>> updateCompte(@PathVariable UUID id, @Valid @RequestBody CompteDTO compteDTO) {
        try {
            CompteDTO updatedCompte = compteService.updateCompte(id, compteDTO);
            return ResponseEntity.ok(ApiResponse.success(SuccessMessages.COMPTE_UPDATED_SUCCESSFULLY, updatedCompte));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(ErrorMessages.COMPTE_NOT_FOUND, null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un compte", description = "Supprime un compte bancaire du système")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte supprimé avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Erreur lors de la suppression")
    })
    public ResponseEntity<ApiResponse<Void>> deleteCompte(@PathVariable UUID id) {
        try {
            if (compteService.deleteCompte(id)) {
                return ResponseEntity.ok(ApiResponse.success(SuccessMessages.COMPTE_DELETED_SUCCESSFULLY, null));
            }
            return ResponseEntity.ok(ApiResponse.error(ErrorMessages.COMPTE_NOT_FOUND, null));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(ErrorMessages.COMPTE_DELETION_FAILED, null));
        }
    }

    @PutMapping("/{id}/block")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Bloquer un compte", description = "Bloque un compte bancaire avec un motif")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte bloqué avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<ApiResponse<CompteDTO>> blockCompte(@PathVariable UUID id, @Valid @RequestBody CompteBlockDTO blockDTO) {
        try {
            CompteDTO blockedCompte = compteService.blockCompte(id, blockDTO.getMotif());
            return ResponseEntity.ok(ApiResponse.success("Compte bloqué avec succès", blockedCompte));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(ErrorMessages.COMPTE_NOT_FOUND, null));
        }
    }

    @PutMapping("/{id}/unblock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Débloquer un compte", description = "Débloque un compte bancaire")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte débloqué avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<ApiResponse<CompteDTO>> unblockCompte(@PathVariable UUID id) {
        try {
            CompteDTO unblockedCompte = compteService.unblockCompte(id);
            return ResponseEntity.ok(ApiResponse.success("Compte débloqué avec succès", unblockedCompte));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(ErrorMessages.COMPTE_NOT_FOUND, null));
        }
    }

    @PutMapping("/{id}/close")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Fermer un compte", description = "Ferme un compte bancaire (solde doit être nul)")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte fermé avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Impossible de fermer le compte"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<ApiResponse<CompteDTO>> closeCompte(@PathVariable UUID id) {
        try {
            CompteDTO closedCompte = compteService.closeCompte(id);
            return ResponseEntity.ok(ApiResponse.success("Compte fermé avec succès", closedCompte));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error("Cannot close account", null));
        }
    }

    @GetMapping("/{id}/transactions")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Obtenir l'historique des transactions", description = "Récupère l'historique des transactions d'un compte")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Historique récupéré avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactionHistory(@PathVariable UUID id) {
        try {
            List<TransactionDTO> transactions = compteService.getTransactionHistory(id);
            return ResponseEntity.ok(ApiResponse.success("Historique des transactions récupéré avec succès", transactions));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(ErrorMessages.COMPTE_NOT_FOUND, null));
        }
    }

    @PutMapping("/{id}/balance")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Mettre à jour le solde du compte", description = "Effectue un dépôt ou un retrait sur le compte")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Solde mis à jour avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solde insuffisant ou opération invalide"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<ApiResponse<CompteDTO>> updateBalance(@PathVariable UUID id, @Valid @RequestBody BalanceUpdateDTO balanceUpdateDTO) {
        try {
            CompteDTO updatedCompte = compteService.updateBalance(id, balanceUpdateDTO.getAmount(),
                    balanceUpdateDTO.getOperationType(), balanceUpdateDTO.getDescription());
            return ResponseEntity.ok(ApiResponse.success("Solde mis à jour avec succès", updatedCompte));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error("Operation failed", null));
        }
    }
}