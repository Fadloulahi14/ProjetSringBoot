package BankODC.BankODC.controller;

import BankODC.BankODC.entity.Admin;
import BankODC.BankODC.service.AdminService;
import BankODC.BankODC.exception.AdminException;
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
@RequestMapping("/api/admins")
@Tag(name = "Administrateurs", description = "API de gestion des administrateurs")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    @Operation(summary = "Obtenir tous les administrateurs", description = "Récupère la liste de tous les administrateurs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des administrateurs récupérée avec succès")
    })
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un administrateur par ID", description = "Récupère un administrateur spécifique par son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrateur trouvé"),
        @ApiResponse(responseCode = "404", description = "Administrateur non trouvé")
    })
    public ResponseEntity<Admin> getAdminById(@PathVariable UUID id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel administrateur", description = "Ajoute un nouvel administrateur au système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrateur créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        try {
            return ResponseEntity.ok(adminService.saveAdmin(admin));
        } catch (AdminException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un administrateur", description = "Modifie les informations d'un administrateur existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrateur mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Administrateur non trouvé")
    })
    public ResponseEntity<Admin> updateAdmin(@PathVariable UUID id, @RequestBody Admin admin) {
        try {
            return ResponseEntity.ok(adminService.updateAdmin(id, admin));
        } catch (AdminException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un administrateur", description = "Supprime un administrateur du système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Administrateur supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Administrateur non trouvé"),
        @ApiResponse(responseCode = "400", description = "Erreur lors de la suppression")
    })
    public ResponseEntity<Void> deleteAdmin(@PathVariable UUID id) {
        try {
            if (adminService.deleteAdmin(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (AdminException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}