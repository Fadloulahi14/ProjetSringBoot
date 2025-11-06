package BankODC.BankODC.service;

import BankODC.BankODC.dto.ClientDTO;
import BankODC.BankODC.entity.Client;
import BankODC.BankODC.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<ClientDTO> getClientById(UUID id) {
        return clientRepository.findById(id)
                .map(client -> modelMapper.map(client, ClientDTO.class));
    }

    public ClientDTO saveClient(ClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);
        Client savedClient = clientRepository.save(client);
        return modelMapper.map(savedClient, ClientDTO.class);
    }

    public void deleteClient(UUID id) {
        clientRepository.deleteById(id);
    }
}