package BankODC.BankODC.controller;

import BankODC.BankODC.constants.ApiResponse;
import BankODC.BankODC.dto.request.ClientRequest;
import BankODC.BankODC.dto.response.ClientResponse;
import BankODC.BankODC.service.interfaces.ClientService;
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
@RequestMapping("/api/clients")
@Tag(name = "Clients", description = "API de gestion des clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private MessageUtil messageUtil;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtenir tous les clients", description = "Récupère la liste de tous les clients")
    public ResponseEntity<ApiResponse<List<ClientResponse>>> getAllClients() {
        List<ClientResponse> clients = clientService.getAllClients().stream()
            .map(client -> new ClientResponse(client.getId(), client.getUserId(), client.getNom(),
                client.getPrenom(), client.getEmail(), client.getTelephone(),
                client.getAdresse(), client.getDateNaissance()))
            .toList();
        String successMessage = messageUtil.getSuccessMessage("clients_retrieved_successfully");
        return ResponseEntity.ok(ApiResponse.success(successMessage, clients));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Obtenir un client par ID", description = "Récupère un client spécifique par son identifiant")
    public ResponseEntity<ApiResponse<ClientResponse>> getClientById(@PathVariable UUID id) {
        Optional<ClientResponse> client = clientService.getClientById(id)
            .map(c -> new ClientResponse(c.getId(), c.getUserId(), c.getNom(),
                c.getPrenom(), c.getEmail(), c.getTelephone(),
                c.getAdresse(), c.getDateNaissance()));
        if (client.isPresent()) {
            String successMessage = messageUtil.getSuccessMessage("client_retrieved_successfully");
            return ResponseEntity.ok(ApiResponse.success(successMessage, client.get()));
        } else {
            String errorMessage = messageUtil.getErrorMessage("client_not_found");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer un nouveau client", description = "Ajoute un nouveau client au système")
    public ResponseEntity<ApiResponse<ClientResponse>> createClient(@Valid @RequestBody ClientRequest clientRequest) {
        try {
            ClientResponse savedClient = clientService.saveClientFromRequest(clientRequest);
            String successMessage = messageUtil.getSuccessMessage("client_created_successfully");
            return ResponseEntity.ok(ApiResponse.success(successMessage, savedClient));
        } catch (Exception e) {
            String errorMessage = messageUtil.getErrorMessage("client_creation_failed");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour un client", description = "Modifie les informations d'un client existant")
    public ResponseEntity<ApiResponse<ClientResponse>> updateClient(@PathVariable UUID id, @Valid @RequestBody ClientRequest clientRequest) {
        try {
            ClientResponse updatedClient = clientService.updateClientFromRequest(id, clientRequest);
            String successMessage = messageUtil.getSuccessMessage("client_updated_successfully");
            return ResponseEntity.ok(ApiResponse.success(successMessage, updatedClient));
        } catch (Exception e) {
            String errorMessage = messageUtil.getErrorMessage("client_not_found");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un client", description = "Supprime un client du système")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable UUID id) {
        Optional<ClientResponse> client = clientService.getClientById(id);
        if (client.isPresent()) {
            clientService.deleteClient(id);
            String successMessage = messageUtil.getSuccessMessage("client_deleted_successfully");
            return ResponseEntity.ok(ApiResponse.success(successMessage, null));
        } else {
            String errorMessage = messageUtil.getErrorMessage("client_not_found");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }
}