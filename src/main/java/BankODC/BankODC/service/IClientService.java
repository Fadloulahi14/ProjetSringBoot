package BankODC.BankODC.service;

import BankODC.BankODC.dto.ClientDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClientService {
    List<ClientDTO> getAllClients();
    Optional<ClientDTO> getClientById(UUID id);
    ClientDTO saveClient(ClientDTO clientDTO);
    void deleteClient(UUID id);
}