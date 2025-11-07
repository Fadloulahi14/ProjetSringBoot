package BankODC.BankODC.controller;

import BankODC.BankODC.dto.ClientDTO;
import BankODC.BankODC.service.IClientService;
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
@RequestMapping("/api/clients")
@Tag(name = "Clients", description = "API de gestion des clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtenir tous les clients", description = "Récupère la liste de tous les clients")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des clients récupérée avec succès")
    })
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Obtenir un client par ID", description = "Récupère un client spécifique par son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client trouvé"),
        @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    public ResponseEntity<ClientDTO> getClientById(@PathVariable UUID id) {
        Optional<ClientDTO> client = clientService.getClientById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer un nouveau client", description = "Ajoute un nouveau client au système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ClientDTO createClient(@Valid @RequestBody ClientDTO clientDTO) {
        return clientService.saveClient(clientDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour un client", description = "Modifie les informations d'un client existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    public ResponseEntity<ClientDTO> updateClient(@PathVariable UUID id, @Valid @RequestBody ClientDTO clientDetails) {
        Optional<ClientDTO> existingClient = clientService.getClientById(id);
        if (existingClient.isPresent()) {
            clientDetails.setId(id);
            ClientDTO updatedClient = clientService.saveClient(clientDetails);
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un client", description = "Supprime un client du système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Client supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        Optional<ClientDTO> client = clientService.getClientById(id);
        if (client.isPresent()) {
            clientService.deleteClient(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}