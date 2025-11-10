package BankODC.BankODC.controller;

import BankODC.BankODC.constants.ApiResponse;
import BankODC.BankODC.dto.AdminCreateDTO;
import BankODC.BankODC.dto.request.AdminRequest;
import BankODC.BankODC.dto.response.AdminResponse;
import BankODC.BankODC.service.interfaces.AdminService;
import BankODC.BankODC.exception.AdminException;
import BankODC.BankODC.util.MessageUtil;
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
@RequestMapping("/api/admins")
@Tag(name = "Administrateurs", description = "API de gestion des administrateurs")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    @Operation(summary = "Obtenir tous les administrateurs", description = "Récupère la liste de tous les administrateurs")
    public ResponseEntity<ApiResponse<List<AdminResponse>>> getAllAdmins() {
        List<AdminResponse> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(ApiResponse.success("Administrateurs récupérés avec succès", admins));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un administrateur par ID", description = "Récupère un administrateur spécifique par son identifiant")
    public ResponseEntity<ApiResponse<AdminResponse>> getAdminById(@PathVariable UUID id) {
        return adminService.getAdminById(id)
                .map(admin -> ResponseEntity.ok(ApiResponse.success("Administrateur trouvé", admin)))
                .orElse(ResponseEntity.ok(ApiResponse.error("Administrateur non trouvé", null)));
    }



    @PostMapping
    @Operation(summary = "Créer un nouvel administrateur", description = "Ajoute un nouvel administrateur au système")
    public ResponseEntity<ApiResponse<AdminResponse>> createAdmin(@Valid @RequestBody AdminCreateDTO adminCreateDTO) {
        try {
            AdminResponse createdAdmin = adminService.createAdmin(adminCreateDTO);
            return ResponseEntity.ok(ApiResponse.success("Administrateur créé avec succès", createdAdmin));
        } catch (AdminException e) {
            return ResponseEntity.ok(ApiResponse.error("Erreur lors de la création", null));
        }
    }



    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un administrateur", description = "Modifie les informations d'un administrateur existant")
    public ResponseEntity<ApiResponse<AdminResponse>> updateAdmin(@PathVariable UUID id, @Valid @RequestBody AdminRequest adminRequest) {
        try {
            AdminResponse updatedAdmin = adminService.updateAdminFromRequest(id, adminRequest);
            return ResponseEntity.ok(ApiResponse.success("Administrateur mis à jour avec succès", updatedAdmin));
        } catch (AdminException e) {
            return ResponseEntity.ok(ApiResponse.error("Administrateur non trouvé", null));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un administrateur", description = "Supprime un administrateur du système")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(@PathVariable UUID id) {
        try {
            if (adminService.deleteAdmin(id)) {
                return ResponseEntity.ok(ApiResponse.success("Administrateur supprimé avec succès", null));
            }
            return ResponseEntity.ok(ApiResponse.error("Administrateur non trouvé", null));
        } catch (AdminException e) {
            return ResponseEntity.ok(ApiResponse.error("Erreur lors de la suppression", null));
        }
    }
}