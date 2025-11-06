package BankODC.BankODC.controller;

import BankODC.BankODC.constants.ApiResponse;
import BankODC.BankODC.constants.ErrorMessages;
import BankODC.BankODC.constants.SuccessMessages;
import BankODC.BankODC.dto.CompteDTO;
import BankODC.BankODC.service.ICompteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comptes")
@Tag(name = "Comptes", description = "API de gestion des comptes bancaires")
public class CompteController {

    @Autowired
    private ICompteService compteService;

    @GetMapping
    @Operation(summary = "Obtenir tous les comptes", description = "Récupère la liste de tous les comptes bancaires")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Liste des comptes récupérée avec succès")
    })
    public ResponseEntity<ApiResponse<List<CompteDTO>>> getAllComptes() {
        List<CompteDTO> comptes = compteService.getAllComptes();
        return ResponseEntity.ok(ApiResponse.success(SuccessMessages.COMPTES_RETRIEVED_SUCCESSFULLY, comptes));
    }

    @GetMapping("/{id}")
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
    @Operation(summary = "Créer un nouveau compte", description = "Ajoute un nouveau compte bancaire au système")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Compte créé avec succès"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<ApiResponse<CompteDTO>> createCompte(@Valid @RequestBody CompteDTO compteDTO) {
        try {
            CompteDTO savedCompte = compteService.saveCompte(compteDTO);
            return ResponseEntity.ok(ApiResponse.success(SuccessMessages.COMPTE_CREATED_SUCCESSFULLY, savedCompte));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(ErrorMessages.INVALID_DATA, null));
        }
    }

    @PutMapping("/{id}")
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
}