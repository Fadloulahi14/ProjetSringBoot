package BankODC.BankODC.service.interfaces;

import BankODC.BankODC.dto.request.ClientRequest;
import BankODC.BankODC.dto.response.ClientResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientService {
    // Legacy methods (keep for backward compatibility)
    List<ClientResponse> getAllClients();
    Optional<ClientResponse> getClientById(UUID id);
    ClientResponse saveClient(ClientResponse clientDTO);
    void deleteClient(UUID id);

    // New methods for separated DTOs
    List<ClientResponse> getAllClientsResponse();
    Optional<ClientResponse> getClientByIdResponse(UUID id);
    ClientResponse saveClientFromRequest(ClientRequest clientRequest);
    ClientResponse updateClientFromRequest(UUID id, ClientRequest clientRequest);
}