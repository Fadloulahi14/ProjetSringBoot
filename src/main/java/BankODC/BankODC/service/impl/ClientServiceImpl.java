package BankODC.BankODC.service.impl;

import BankODC.BankODC.dto.request.ClientRequest;
import BankODC.BankODC.dto.response.ClientResponse;
import BankODC.BankODC.entity.Client;
import BankODC.BankODC.repository.ClientRepository;
import BankODC.BankODC.service.interfaces.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Legacy methods (backward compatibility)
    public List<ClientResponse> getAllClients() {
        return getAllClientsResponse();
    }

    public Optional<ClientResponse> getClientById(UUID id) {
        return getClientByIdResponse(id);
    }

    public ClientResponse saveClient(ClientResponse clientDTO) {
        // Convert ClientResponse to Client entity
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setUserId(clientDTO.getUserId());
        client.setNom(clientDTO.getNom());
        client.setPrenom(clientDTO.getPrenom());
        client.setEmail(clientDTO.getEmail());
        client.setTelephone(clientDTO.getTelephone());
        client.setAdresse(clientDTO.getAdresse());
        client.setDateNaissance(clientDTO.getDateNaissance());

        Client savedClient = clientRepository.save(client);
        return new ClientResponse(savedClient.getId(), savedClient.getUserId(),
            savedClient.getNom(), savedClient.getPrenom(), savedClient.getEmail(),
            savedClient.getTelephone(), savedClient.getAdresse(), savedClient.getDateNaissance());
    }

    public void deleteClient(UUID id) {
        clientRepository.deleteById(id);
    }

    // New methods for separated DTOs
    @Override
    public List<ClientResponse> getAllClientsResponse() {
        return clientRepository.findAll()
                .stream()
                .map(client -> new ClientResponse(client.getId(), client.getUserId(),
                    client.getNom(), client.getPrenom(), client.getEmail(),
                    client.getTelephone(), client.getAdresse(), client.getDateNaissance()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClientResponse> getClientByIdResponse(UUID id) {
        return clientRepository.findById(id)
                .map(client -> new ClientResponse(client.getId(), client.getUserId(),
                    client.getNom(), client.getPrenom(), client.getEmail(),
                    client.getTelephone(), client.getAdresse(), client.getDateNaissance()));
    }

    @Override
    public ClientResponse saveClientFromRequest(ClientRequest clientRequest) {
        Client client = new Client();
        client.setUserId(clientRequest.getUserId());
        client.setNom(clientRequest.getNom());
        client.setPrenom(clientRequest.getPrenom());
        client.setEmail(clientRequest.getEmail());
        client.setTelephone(clientRequest.getTelephone());
        client.setAdresse(clientRequest.getAdresse());
        client.setDateNaissance(clientRequest.getDateNaissance());

        Client savedClient = clientRepository.save(client);
        return new ClientResponse(savedClient.getId(), savedClient.getUserId(),
            savedClient.getNom(), savedClient.getPrenom(), savedClient.getEmail(),
            savedClient.getTelephone(), savedClient.getAdresse(), savedClient.getDateNaissance());
    }

    @Override
    public ClientResponse updateClientFromRequest(UUID id, ClientRequest clientRequest) {
        Client client = clientRepository.findById(id).orElseThrow();
        client.setUserId(clientRequest.getUserId());
        client.setNom(clientRequest.getNom());
        client.setPrenom(clientRequest.getPrenom());
        client.setEmail(clientRequest.getEmail());
        client.setTelephone(clientRequest.getTelephone());
        client.setAdresse(clientRequest.getAdresse());
        client.setDateNaissance(clientRequest.getDateNaissance());

        Client updatedClient = clientRepository.save(client);
        return new ClientResponse(updatedClient.getId(), updatedClient.getUserId(),
            updatedClient.getNom(), updatedClient.getPrenom(), updatedClient.getEmail(),
            updatedClient.getTelephone(), updatedClient.getAdresse(), updatedClient.getDateNaissance());
    }
}