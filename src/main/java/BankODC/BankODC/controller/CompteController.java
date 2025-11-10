package BankODC.BankODC.controller;

import BankODC.BankODC.constants.ApiResponse;
import BankODC.BankODC.dto.CompteCreateDTO;
import BankODC.BankODC.dto.BalanceUpdateDTO;
import BankODC.BankODC.dto.CompteBlockDTO;
import BankODC.BankODC.dto.PaginationResponse;
import BankODC.BankODC.dto.request.CompteRequest;
import BankODC.BankODC.dto.response.CompteResponse;
import BankODC.BankODC.dto.response.TransactionResponse;
import BankODC.BankODC.service.interfaces.CompteService;
import BankODC.BankODC.util.MessageUtil;
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
    private CompteService compteService;

    @Autowired
    private MessageUtil messageUtil;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Obtenir les comptes avec pagination et filtrage", description = "Récupère la liste paginée des comptes bancaires avec possibilité de filtrage par type et utilisateur")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Liste des comptes récupérée avec succès")
    })
    public ResponseEntity<ApiResponse<PaginationResponse<CompteResponse>>> getComptesPaginated(
            @Parameter(description = "Numéro de page (commence à 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Champ de tri") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Direction de tri (asc/desc)") @RequestParam(defaultValue = "asc") String sortDir,
            @Parameter(description = "Type de compte pour filtrer") @RequestParam(required = false) String type,
            @Parameter(description = "ID utilisateur pour filtrer") @RequestParam(required = false) UUID userid) {
        Pageable pageable = PageRequest.of(page, size, org.springframework.data.domain.Sort.by(
                sortDir.equalsIgnoreCase("desc") ? org.springframework.data.domain.Sort.Direction.DESC :
                org.springframework.data.domain.Sort.Direction.ASC, sortBy));
        PaginationResponse<CompteResponse> comptes = compteService.getComptesPaginated(pageable, type, userid);
        String successMessage = messageUtil.getSuccessMessage("comptes_retrieved_successfully");
        return ResponseEntity.ok(ApiResponse.success(successMessage, comptes));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Obtenir un compte par ID", description = "Récupère un compte bancaire spécifique par son identifiant")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte trouvé"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<ApiResponse<CompteResponse>> getCompteById(@PathVariable UUID id) {
        return compteService.getCompteById(id)
                .map(compte -> {
                    CompteResponse response = new CompteResponse(compte.getId(), compte.getNumeroCompte(),
                        compte.getTitulaire(), compte.getType(), compte.getSolde(), compte.getDevise(),
                        compte.getDateCreation(), compte.getStatut(), compte.getMotifBlocage(),
                        compte.getMetadonnees(), compte.getUserName());
                    String successMessage = messageUtil.getSuccessMessage("compte_retrieved_successfully");
                    return ResponseEntity.ok(ApiResponse.success(successMessage, response));
                })
                .orElse(ResponseEntity.ok(ApiResponse.error(messageUtil.getErrorMessage("compte_not_found"), null)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer un nouveau compte avec client", description = "Crée un compte bancaire avec un nouveau client ou utilise un client existant")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte créé avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<ApiResponse<CompteResponse>> createCompte(@RequestBody CompteCreateDTO compteCreateDTO) {

        try {
            CompteResponse savedCompte = compteService.createCompteWithClient(compteCreateDTO);
            String successMessage = messageUtil.getSuccessMessage("compte_created_successfully");
            return ResponseEntity.ok(ApiResponse.success(successMessage, savedCompte));
        } catch (Exception e) {

            e.printStackTrace();

            String errorMessage = messageUtil.getErrorMessage("compte_creation_failed");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour un compte", description = "Modifie les informations d'un compte bancaire existant")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte mis à jour avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<ApiResponse<CompteResponse>> updateCompte(@PathVariable UUID id, @Valid @RequestBody CompteRequest compteRequest) {
        try {
            CompteResponse updatedCompte = compteService.updateCompteFromRequest(id, compteRequest);
            String successMessage = messageUtil.getSuccessMessage("compte_updated_successfully");
            return ResponseEntity.ok(ApiResponse.success(successMessage, updatedCompte));
        } catch (RuntimeException e) {
            String errorMessage = messageUtil.getErrorMessage("compte_not_found");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
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
                String successMessage = messageUtil.getSuccessMessage("compte_deleted_successfully");
                return ResponseEntity.ok(ApiResponse.success(successMessage, null));
            }
            String errorMessage = messageUtil.getErrorMessage("compte_not_found");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        } catch (RuntimeException e) {
            String errorMessage = messageUtil.getErrorMessage("compte_deletion_failed");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }

    @PutMapping("/{id}/block")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Bloquer un compte", description = "Bloque un compte bancaire avec un motif")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte bloqué avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<ApiResponse<CompteResponse>> blockCompte(@PathVariable UUID id, @Valid @RequestBody CompteBlockDTO blockDTO) {
        try {
            CompteResponse blockedCompte = compteService.blockCompte(id, blockDTO.getMotif());
            String successMessage = messageUtil.getMessage("success.compte_blocked_successfully", new Object[]{blockDTO.getMotif()});
            return ResponseEntity.ok(ApiResponse.success(successMessage, blockedCompte));
        } catch (RuntimeException e) {
            String errorMessage = messageUtil.getErrorMessage("compte_not_found");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }

    @PutMapping("/{id}/unblock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Débloquer un compte", description = "Débloque un compte bancaire")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte débloqué avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<ApiResponse<CompteResponse>> unblockCompte(@PathVariable UUID id) {
        try {
            CompteResponse unblockedCompte = compteService.unblockCompte(id);
            String successMessage = messageUtil.getMessage("success.compte_unblocked_successfully");
            return ResponseEntity.ok(ApiResponse.success(successMessage, unblockedCompte));
        } catch (RuntimeException e) {
            String errorMessage = messageUtil.getErrorMessage("compte_not_found");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
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
    public ResponseEntity<ApiResponse<CompteResponse>> closeCompte(@PathVariable UUID id) {
        try {
            CompteResponse closedCompte = compteService.closeCompte(id);
            String successMessage = messageUtil.getMessage("success.compte_closed_successfully");
            return ResponseEntity.ok(ApiResponse.success(successMessage, closedCompte));
        } catch (RuntimeException e) {
            String errorMessage = messageUtil.getMessage("error.compte_cannot_close");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }

    @GetMapping("/{id}/transactions")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Obtenir l'historique des transactions", description = "Récupère l'historique des transactions d'un compte")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Historique récupéré avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransactionHistory(@PathVariable UUID id) {
        try {
            List<TransactionResponse> transactions = compteService.getTransactionHistory(id);
            String successMessage = messageUtil.getMessage("success.transaction_history_retrieved");
            return ResponseEntity.ok(ApiResponse.success(successMessage, transactions));
        } catch (RuntimeException e) {
            String errorMessage = messageUtil.getErrorMessage("compte_not_found");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
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
    public ResponseEntity<ApiResponse<CompteResponse>> updateBalance(@PathVariable UUID id, @Valid @RequestBody BalanceUpdateDTO balanceUpdateDTO) {
        try {
            CompteResponse updatedCompte = compteService.updateBalance(id, balanceUpdateDTO.getAmount(),
                    balanceUpdateDTO.getOperationType(), balanceUpdateDTO.getDescription());
            String successMessage = messageUtil.getSuccessMessage("compte_balance_updated");
            return ResponseEntity.ok(ApiResponse.success(successMessage, updatedCompte));
        } catch (RuntimeException e) {
            String errorMessage = messageUtil.getErrorMessage("operation_failed");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }
}