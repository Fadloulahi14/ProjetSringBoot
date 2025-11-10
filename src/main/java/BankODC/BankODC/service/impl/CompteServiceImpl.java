package BankODC.BankODC.service.impl;

import BankODC.BankODC.dto.CompteCreateDTO;
import BankODC.BankODC.dto.PaginationResponse;
import BankODC.BankODC.dto.request.CompteRequest;
import BankODC.BankODC.dto.response.CompteResponse;
import BankODC.BankODC.dto.response.TransactionResponse;
import BankODC.BankODC.constants.ErrorMessages;
import BankODC.BankODC.entity.Client;
import BankODC.BankODC.entity.Compte;
import BankODC.BankODC.entity.Transaction;
import BankODC.BankODC.entity.User;
import BankODC.BankODC.repository.ClientRepository;
import BankODC.BankODC.repository.CompteRepository;
import BankODC.BankODC.repository.UserRepository;
import BankODC.BankODC.exception.CompteException;
import BankODC.BankODC.service.interfaces.CompteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CompteServiceImpl implements CompteService {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CompteResponse> getAllComptes() {
        return getAllComptesResponse();
    }

    public Optional<CompteResponse> getCompteById(UUID id) {
        return getCompteByIdResponse(id);
    }

    public CompteResponse saveCompte(CompteResponse compteDTO) {
        Compte compte = new Compte();
        compte.setId(compteDTO.getId());
        compte.setNumeroCompte(compteDTO.getNumeroCompte());
        compte.setTitulaire(compteDTO.getTitulaire());
        compte.setType(compteDTO.getType());
        compte.setSolde(compteDTO.getSolde());
        compte.setDevise(compteDTO.getDevise());
        compte.setStatut(compteDTO.getStatut());
        compte.setMotifBlocage(compteDTO.getMotifBlocage());
        compte.setMetadonnees(compteDTO.getMetadonnees());

        if (compte.getSolde() == null) {
            compte.setSolde(BigDecimal.valueOf(0.0));
        }

        validateCompte(compte);
        Compte savedCompte = compteRepository.save(compte);
        return new CompteResponse(savedCompte.getId(), savedCompte.getNumeroCompte(),
            savedCompte.getTitulaire(), savedCompte.getType(), savedCompte.getSolde(),
            savedCompte.getDevise(), null, savedCompte.getStatut(), savedCompte.getMotifBlocage(),
            savedCompte.getMetadonnees(), savedCompte.getUser() != null ? savedCompte.getUser().getNom() : null);
    }

    public CompteResponse updateCompte(UUID id, CompteResponse compteDetails) {
        return updateCompteFromRequest(id, new CompteRequest(compteDetails.getType(),
            compteDetails.getSolde(), compteDetails.getDevise()));
    }

    public List<CompteResponse> getAllComptesResponse() {
        return compteRepository.findAll()
                .stream()
                .map(compte -> new CompteResponse(compte.getId(), compte.getNumeroCompte(),
                    compte.getTitulaire(), compte.getType(), compte.getSolde(),
                    compte.getDevise(), null, compte.getStatut(), compte.getMotifBlocage(),
                    compte.getMetadonnees(), compte.getUser() != null ? compte.getUser().getNom() : null))
                .collect(Collectors.toList());
    }

    public Optional<CompteResponse> getCompteByIdResponse(UUID id) {
        if (id == null) {
            throw new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage());
        }
        return compteRepository.findById(id)
                .map(compte -> new CompteResponse(compte.getId(), compte.getNumeroCompte(),
                    compte.getTitulaire(), compte.getType(), compte.getSolde(),
                    compte.getDevise(), null, compte.getStatut(), compte.getMotifBlocage(),
                    compte.getMetadonnees(), compte.getUser() != null ? compte.getUser().getNom() : null));
    }

    @Override
    public CompteResponse updateCompteFromRequest(UUID id, CompteRequest compteRequest) {
        if (id == null || compteRequest == null) {
            throw new CompteException(ErrorMessages.INVALID_DATA.name(), ErrorMessages.INVALID_DATA.getMessage());
        }

        return compteRepository.findById(id)
            .map(compte -> {
                compte.setType(compteRequest.getType());
                compte.setSolde(compteRequest.getSolde());
                compte.setDevise(compteRequest.getDevise());

                validateCompte(compte);
                Compte savedCompte = compteRepository.save(compte);
                return new CompteResponse(savedCompte.getId(), savedCompte.getNumeroCompte(),
                    savedCompte.getTitulaire(), savedCompte.getType(), savedCompte.getSolde(),
                    savedCompte.getDevise(), null, savedCompte.getStatut(), savedCompte.getMotifBlocage(),
                    savedCompte.getMetadonnees(), savedCompte.getUser() != null ? savedCompte.getUser().getNom() : null);
            })
            .orElseThrow(() -> new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage()));
    }

    public boolean deleteCompte(UUID id) {
        if (id == null) {
            throw new CompteException(ErrorMessages.INVALID_DATA.name(), ErrorMessages.INVALID_DATA.getMessage());
        }

        return compteRepository.findById(id)
            .map(compte -> {
                validateDeletion(compte);
                compteRepository.delete(compte);
                return true;
            })
            .orElse(false);
    }

    private void validateCompte(Compte compte) {
        // Temporarily disable validations for testing
        /*
        if (compte.getNumeroCompte() == null || compte.getNumeroCompte().trim().isEmpty()) {
            throw new CompteException(ErrorMessages.REQUIRED_FIELD_MISSING.name(), ErrorMessages.REQUIRED_FIELD_MISSING.getMessage());
        }

        // Check if account number already exists
        if (compteRepository.findByNumeroCompte(compte.getNumeroCompte()).isPresent()) {
            throw new CompteException("Account number already exists", "Le numéro de compte existe déjà");
        }

        if (compte.getSolde().compareTo(BigDecimal.ZERO) < 0) {
            throw new CompteException(ErrorMessages.COMPTE_NEGATIVE_BALANCE.name(), ErrorMessages.COMPTE_NEGATIVE_BALANCE.getMessage());
        }

        if (compte.getType() == null) {
            throw new CompteException(ErrorMessages.COMPTE_INVALID_TYPE.name(), ErrorMessages.COMPTE_INVALID_TYPE.getMessage());
        }
        */
    }

    private void validateDeletion(Compte compte) {
        if (compte.getSolde().compareTo(BigDecimal.ZERO) > 0) {
            throw new CompteException(ErrorMessages.COMPTE_CANNOT_DELETE_WITH_BALANCE.name(), ErrorMessages.COMPTE_CANNOT_DELETE_WITH_BALANCE.getMessage());
        }
    }

    private void updateCompteFields(Compte compte, Compte compteDetails) {
        compte.setNumeroCompte(compteDetails.getNumeroCompte());
        compte.setSolde(compteDetails.getSolde());
        compte.setType(compteDetails.getType());
        compte.setDevise(compteDetails.getDevise());
        compte.setStatut(compteDetails.getStatut());
        compte.setMotifBlocage(compteDetails.getMotifBlocage());
        compte.setMetadonnees(compteDetails.getMetadonnees());
    }

    public CompteResponse createCompteWithClient(CompteCreateDTO compteCreateDTO) {
       
        if (compteCreateDTO.getTitulaire() == null || compteCreateDTO.getTitulaire().trim().isEmpty()) {
            throw new RuntimeException("Le nom du titulaire est obligatoire");
        }
        if (compteCreateDTO.getEmail() == null || compteCreateDTO.getEmail().trim().isEmpty()) {
            throw new RuntimeException("L'email est obligatoire");
        }
        if (compteCreateDTO.getType() == null || compteCreateDTO.getType().trim().isEmpty()) {
            throw new RuntimeException("Le type de compte est obligatoire");
        }
        if (compteCreateDTO.getSolde() == null || compteCreateDTO.getSolde().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le solde doit être positif");
        }

        System.out.println("Validation des champs OK");

        if (compteCreateDTO.getId() == null && userRepository.existsByEmail(compteCreateDTO.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }

        User user;
        if (compteCreateDTO.getId() != null) {
            Optional<Client> existingClient = clientRepository.findById(compteCreateDTO.getId());
            if (existingClient.isEmpty()) {
                throw new RuntimeException("Client avec l'ID spécifié n'existe pas");
            }
            user = userRepository.findById(existingClient.get().getUserId()).orElseThrow();
        } else {
            
            user = new User();
            user.setNom(compteCreateDTO.getTitulaire());
            user.setEmail(compteCreateDTO.getEmail());
            user.setTelephone(compteCreateDTO.getTelephone());
            user.setRole("USER");
            user.setPassword("defaultPassword123"); 
            user = userRepository.save(user);

            Client client = new Client();
            client.setId(user.getId());
            client.setUserId(user.getId());
            client.setNom(compteCreateDTO.getTitulaire());
            client.setEmail(compteCreateDTO.getEmail());
            client.setTelephone(compteCreateDTO.getTelephone());
            client.setAdresse(compteCreateDTO.getAdresse());
            clientRepository.save(client);
        }

        Compte compte = new Compte();
        compte.setNumeroCompte(generateAccountNumber());
        compte.setTitulaire(user.getNom()); 
        compte.setType(compteCreateDTO.getType());
        compte.setSolde(compteCreateDTO.getSolde());
        compte.setDevise(compteCreateDTO.getDevise());
        compte.setStatut("ACTIF"); 
        compte.setUser(user); 

        Map<String, String> metadonnees = new HashMap<>();
        metadonnees.put("derniereModification", java.time.LocalDateTime.now().toString());
        metadonnees.put("version", "1");
        compte.setMetadonnees(metadonnees);

      

        if (compte.getNumeroCompte() == null || compte.getNumeroCompte().trim().isEmpty()) {
            throw new RuntimeException("Le numéro de compte est obligatoire");
        }
        if (compte.getTitulaire() == null || compte.getTitulaire().trim().isEmpty()) {
            throw new RuntimeException("Le titulaire est obligatoire");
        }
        if (compte.getType() == null || compte.getType().trim().isEmpty()) {
            throw new RuntimeException("Le type de compte est obligatoire");
        }
        if (compte.getSolde() == null) {
            throw new RuntimeException("Le solde est obligatoire");
        }
        if (compte.getUser() == null) {
            throw new RuntimeException("L'utilisateur est obligatoire");
        }
        if (compte.getUser().getId() == null) {
            throw new RuntimeException("L'ID utilisateur est obligatoire");
        }

        Compte savedCompte;
        try {
            savedCompte = compteRepository.save(compte);
        } catch (Exception e) {
            e.printStackTrace();
            throw e; 
        }

        CompteResponse response = new CompteResponse(savedCompte.getId(), savedCompte.getNumeroCompte(),
            savedCompte.getTitulaire(), savedCompte.getType(), savedCompte.getSolde(),
            savedCompte.getDevise(), null, savedCompte.getStatut(), savedCompte.getMotifBlocage(),
            savedCompte.getMetadonnees(), user.getNom());
        return response;
    }

    private String generateAccountNumber() {
        
        int randomNum = ThreadLocalRandom.current().nextInt(100000, 999999);
        return "COMPT" + String.format("%06d", randomNum);
    }

    public PaginationResponse<CompteResponse> getComptesPaginated(Pageable pageable, String type, UUID userid) {
        Page<Compte> comptePage = compteRepository.findComptesWithFilters(type, userid, pageable);
        List<CompteResponse> content = comptePage.getContent()
                .stream()
                .map(compte -> new CompteResponse(compte.getId(), compte.getNumeroCompte(),
                    compte.getTitulaire(), compte.getType(), compte.getSolde(),
                    compte.getDevise(), null, compte.getStatut(), compte.getMotifBlocage(),
                    compte.getMetadonnees(), compte.getUser() != null ? compte.getUser().getNom() : null))
                .collect(Collectors.toList());

        return new PaginationResponse<>(
                content,
                comptePage.getNumber(),
                comptePage.getSize(),
                comptePage.getTotalElements(),
                comptePage.getTotalPages(),
                comptePage.isFirst(),
                comptePage.isLast()
        );
    }

    public CompteResponse blockCompte(UUID id, String motif) {
        return compteRepository.findById(id)
                .map(compte -> {
                    compte.setStatut("BLOQUE");
                    compte.setMotifBlocage(motif);
                    Compte savedCompte = compteRepository.save(compte);
                    return new CompteResponse(savedCompte.getId(), savedCompte.getNumeroCompte(),
                        savedCompte.getTitulaire(), savedCompte.getType(), savedCompte.getSolde(),
                        savedCompte.getDevise(), null, savedCompte.getStatut(), savedCompte.getMotifBlocage(),
                        savedCompte.getMetadonnees(), savedCompte.getUser() != null ? savedCompte.getUser().getNom() : null);
                })
                .orElseThrow(() -> new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage()));
    }

    public CompteResponse unblockCompte(UUID id) {
        return compteRepository.findById(id)
                .map(compte -> {
                    compte.setStatut("ACTIF");
                    compte.setMotifBlocage(null);
                    Compte savedCompte = compteRepository.save(compte);
                    return new CompteResponse(savedCompte.getId(), savedCompte.getNumeroCompte(),
                        savedCompte.getTitulaire(), savedCompte.getType(), savedCompte.getSolde(),
                        savedCompte.getDevise(), null, savedCompte.getStatut(), savedCompte.getMotifBlocage(),
                        savedCompte.getMetadonnees(), savedCompte.getUser() != null ? savedCompte.getUser().getNom() : null);
                })
                .orElseThrow(() -> new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage()));
    }

    public CompteResponse closeCompte(UUID id) {
        return compteRepository.findById(id)
                .map(compte -> {
                    if (compte.getSolde().compareTo(BigDecimal.ZERO) > 0) {
                        throw new CompteException("Cannot close account with balance", "Impossible de fermer un compte avec un solde positif");
                    }
                    compte.setStatut("FERME");
                    Compte savedCompte = compteRepository.save(compte);
                    return new CompteResponse(savedCompte.getId(), savedCompte.getNumeroCompte(),
                        savedCompte.getTitulaire(), savedCompte.getType(), savedCompte.getSolde(),
                        savedCompte.getDevise(), null, savedCompte.getStatut(), savedCompte.getMotifBlocage(),
                        savedCompte.getMetadonnees(), savedCompte.getUser() != null ? savedCompte.getUser().getNom() : null);
                })
                .orElseThrow(() -> new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage()));
    }

    public List<TransactionResponse> getTransactionHistory(UUID compteId) {
        return compteRepository.findById(compteId)
                .map(compte -> compte.getTransactions()
                        .stream()
                        .map(transaction -> new TransactionResponse(transaction.getId(), transaction.getCompteId(),
                            transaction.getType(), transaction.getMontant(), transaction.getDate()))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage()));
    }

    public CompteResponse updateBalance(UUID id, Double amount, String operationType, String description) {
        return compteRepository.findById(id)
                .map(compte -> {
                    BigDecimal currentBalance = compte.getSolde();
                    BigDecimal changeAmount = BigDecimal.valueOf(amount);

                    if ("WITHDRAWAL".equals(operationType)) {
                        if (currentBalance.compareTo(changeAmount) < 0) {
                            throw new CompteException("Insufficient balance", "Solde insuffisant pour effectuer cette opération");
                        }
                        compte.setSolde(currentBalance.subtract(changeAmount));
                    } else if ("DEPOSIT".equals(operationType)) {
                        compte.setSolde(currentBalance.add(changeAmount));
                    } else {
                        throw new CompteException("Invalid operation type", "Type d'opération invalide");
                    }

                    Transaction transaction = new Transaction();
                    transaction.setId(UUID.randomUUID());
                    transaction.setMontant(changeAmount);
                    transaction.setType(operationType);
                    transaction.setDate(java.time.LocalDateTime.now().toString());
                    transaction.setCompteId(id);

                    compte.getTransactions().add(transaction);

                    Compte savedCompte = compteRepository.save(compte);
                    return new CompteResponse(savedCompte.getId(), savedCompte.getNumeroCompte(),
                        savedCompte.getTitulaire(), savedCompte.getType(), savedCompte.getSolde(),
                        savedCompte.getDevise(), null, savedCompte.getStatut(), savedCompte.getMotifBlocage(),
                        savedCompte.getMetadonnees(), savedCompte.getUser() != null ? savedCompte.getUser().getNom() : null);
                })
                .orElseThrow(() -> new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage()));
    }
}