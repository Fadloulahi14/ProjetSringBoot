package BankODC.BankODC.controller;

import BankODC.BankODC.entity.Compte;
import BankODC.BankODC.service.CompteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private CompteService compteService;

    @GetMapping
    @Operation(summary = "Obtenir tous les comptes", description = "Récupère la liste de tous les comptes bancaires")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des comptes récupérée avec succès")
    })
    public ResponseEntity<List<Compte>> getAllComptes() {
        return ResponseEntity.ok(compteService.getAllComptes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un compte par ID", description = "Récupère un compte bancaire spécifique par son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compte trouvé"),
        @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<Compte> getCompteById(@PathVariable UUID id) {
        return compteService.getCompteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau compte", description = "Ajoute un nouveau compte bancaire au système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compte créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Compte> createCompte(@RequestBody Compte compte) {
        try {
            return ResponseEntity.ok(compteService.saveCompte(compte));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un compte", description = "Modifie les informations d'un compte bancaire existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compte mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<Compte> updateCompte(@PathVariable UUID id, @RequestBody Compte compte) {
        try {
            return ResponseEntity.ok(compteService.updateCompte(id, compte));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un compte", description = "Supprime un compte bancaire du système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Compte supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Compte non trouvé"),
        @ApiResponse(responseCode = "400", description = "Erreur lors de la suppression")
    })
    public ResponseEntity<Void> deleteCompte(@PathVariable UUID id) {
        try {
            if (compteService.deleteCompte(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}