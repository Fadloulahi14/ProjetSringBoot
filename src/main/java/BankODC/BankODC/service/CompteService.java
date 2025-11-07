package BankODC.BankODC.service;

import BankODC.BankODC.dto.CompteCreateDTO;
import BankODC.BankODC.dto.CompteDTO;
import BankODC.BankODC.dto.PaginationResponse;
import BankODC.BankODC.dto.TransactionDTO;
import BankODC.BankODC.constants.ErrorMessages;
import BankODC.BankODC.entity.Client;
import BankODC.BankODC.entity.Compte;
import BankODC.BankODC.entity.Transaction;
import BankODC.BankODC.entity.User;
import BankODC.BankODC.repository.ClientRepository;
import BankODC.BankODC.repository.CompteRepository;
import BankODC.BankODC.repository.UserRepository;
import BankODC.BankODC.exception.CompteException;
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
public class CompteService implements ICompteService {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CompteDTO> getAllComptes() {
        return compteRepository.findAll()
                .stream()
                .map(compte -> modelMapper.map(compte, CompteDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<CompteDTO> getCompteById(UUID id) {
        if (id == null) {
            throw new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage());
        }
        return compteRepository.findById(id)
                .map(compte -> modelMapper.map(compte, CompteDTO.class));
    }

    public CompteDTO saveCompte(CompteDTO compteDTO) {
        if (compteDTO == null) {
            throw new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage());
        }

        Compte compte = modelMapper.map(compteDTO, Compte.class);

        if (compte.getSolde() == null) {
            compte.setSolde(BigDecimal.valueOf(0.0));
        }

        validateCompte(compte);
        Compte savedCompte = compteRepository.save(compte);
        return modelMapper.map(savedCompte, CompteDTO.class);
    }

    public CompteDTO updateCompte(UUID id, CompteDTO compteDetails) {
        if (id == null || compteDetails == null) {
            throw new CompteException(ErrorMessages.INVALID_DATA.name(), ErrorMessages.INVALID_DATA.getMessage());
        }

        return compteRepository.findById(id)
            .map(compte -> {
                updateCompteFields(compte, modelMapper.map(compteDetails, Compte.class));
                validateCompte(compte);
                Compte savedCompte = compteRepository.save(compte);
                return modelMapper.map(savedCompte, CompteDTO.class);
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

    public CompteDTO createCompteWithClient(CompteCreateDTO compteCreateDTO) {
        System.out.println("=== DÉBUT CRÉATION COMPTE ===");
        System.out.println("Données reçues: " + compteCreateDTO);

        // Validate required fields
        if (compteCreateDTO.getTitulaire() == null || compteCreateDTO.getTitulaire().trim().isEmpty()) {
            System.out.println("ERREUR: Le nom du titulaire est obligatoire");
            throw new RuntimeException("Le nom du titulaire est obligatoire");
        }
        if (compteCreateDTO.getEmail() == null || compteCreateDTO.getEmail().trim().isEmpty()) {
            System.out.println("ERREUR: L'email est obligatoire");
            throw new RuntimeException("L'email est obligatoire");
        }
        if (compteCreateDTO.getType() == null || compteCreateDTO.getType().trim().isEmpty()) {
            System.out.println("ERREUR: Le type de compte est obligatoire");
            throw new RuntimeException("Le type de compte est obligatoire");
        }
        if (compteCreateDTO.getSolde() == null || compteCreateDTO.getSolde().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("ERREUR: Le solde doit être positif");
            throw new RuntimeException("Le solde doit être positif");
        }

        System.out.println("Validation des champs OK");

        // Check if email already exists for new clients
        if (compteCreateDTO.getId() == null && userRepository.existsByEmail(compteCreateDTO.getEmail())) {
            System.out.println("ERREUR: Un utilisateur avec cet email existe déjà");
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }

        // Create or find user
        User user;
        if (compteCreateDTO.getId() != null) {
            // Existing client
            System.out.println("Recherche client existant avec ID: " + compteCreateDTO.getId());
            Optional<Client> existingClient = clientRepository.findById(compteCreateDTO.getId());
            if (existingClient.isEmpty()) {
                System.out.println("ERREUR: Client avec l'ID spécifié n'existe pas");
                throw new RuntimeException("Client avec l'ID spécifié n'existe pas");
            }
            user = userRepository.findById(existingClient.get().getUserId()).orElseThrow();
            System.out.println("Client existant trouvé: " + user.getNom());
        } else {
            // Create new user and client
            System.out.println("Création d'un nouveau client");
            user = new User();
            user.setNom(compteCreateDTO.getTitulaire());
            user.setEmail(compteCreateDTO.getEmail());
            user.setTelephone(compteCreateDTO.getTelephone());
            user.setRole("USER");
            user.setPassword("defaultPassword123"); // Should be hashed in production
            user = userRepository.save(user);
            System.out.println("Utilisateur créé: " + user.getId());

            Client client = new Client();
            client.setId(user.getId());
            client.setUserId(user.getId());
            client.setNom(compteCreateDTO.getTitulaire());
            client.setEmail(compteCreateDTO.getEmail());
            client.setTelephone(compteCreateDTO.getTelephone());
            client.setAdresse(compteCreateDTO.getAdresse());
            clientRepository.save(client);
            System.out.println("Client créé: " + client.getId());
        }

        // Create account
        System.out.println("Création du compte");
        Compte compte = new Compte();
        compte.setNumeroCompte(generateAccountNumber());
        compte.setTitulaire(user.getNom()); // Use user's name as titulaire
        compte.setType(compteCreateDTO.getType());
        compte.setSolde(compteCreateDTO.getSolde());
        compte.setDevise(compteCreateDTO.getDevise());
        compte.setStatut("ACTIF"); // Default status
        compte.setUser(user); // Set the User entity directly instead of just the ID

        // Initialize metadata
        Map<String, String> metadonnees = new HashMap<>();
        metadonnees.put("derniereModification", java.time.LocalDateTime.now().toString());
        metadonnees.put("version", "1");
        compte.setMetadonnees(metadonnees);

        System.out.println("Sauvegarde du compte...");
        System.out.println("Détails du compte à sauvegarder:");
        System.out.println("- numeroCompte: " + compte.getNumeroCompte());
        System.out.println("- titulaire: " + compte.getTitulaire());
        System.out.println("- type: " + compte.getType());
        System.out.println("- solde: " + compte.getSolde());
        System.out.println("- devise: " + compte.getDevise());
        System.out.println("- statut: " + compte.getStatut());
        System.out.println("- user: " + (compte.getUser() != null ? compte.getUser().getId() : "null"));
        System.out.println("- userid: " + compte.getUserid());
        System.out.println("- metadonnees: " + compte.getMetadonnees());

        // Test de validation manuelle avant sauvegarde
        System.out.println("Validation manuelle du compte...");
        if (compte.getNumeroCompte() == null || compte.getNumeroCompte().trim().isEmpty()) {
            System.out.println("ERREUR: numeroCompte est null ou vide");
            throw new RuntimeException("Le numéro de compte est obligatoire");
        }
        if (compte.getTitulaire() == null || compte.getTitulaire().trim().isEmpty()) {
            System.out.println("ERREUR: titulaire est null ou vide");
            throw new RuntimeException("Le titulaire est obligatoire");
        }
        if (compte.getType() == null || compte.getType().trim().isEmpty()) {
            System.out.println("ERREUR: type est null ou vide");
            throw new RuntimeException("Le type de compte est obligatoire");
        }
        if (compte.getSolde() == null) {
            System.out.println("ERREUR: solde est null");
            throw new RuntimeException("Le solde est obligatoire");
        }
        if (compte.getUser() == null) {
            System.out.println("ERREUR: user est null");
            throw new RuntimeException("L'utilisateur est obligatoire");
        }
        if (compte.getUser().getId() == null) {
            System.out.println("ERREUR: user.id est null");
            throw new RuntimeException("L'ID utilisateur est obligatoire");
        }
        System.out.println("Validation OK, tentative de sauvegarde...");

        Compte savedCompte;
        try {
            savedCompte = compteRepository.save(compte);
            System.out.println("Compte créé avec succès: " + savedCompte.getId());
        } catch (Exception e) {
            System.out.println("ERREUR lors de la sauvegarde du compte: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw pour que le contrôleur l'intercepte
        }

        CompteDTO dto = modelMapper.map(savedCompte, CompteDTO.class);
        dto.setUserName(user.getNom());
        System.out.println("=== FIN CRÉATION COMPTE ===");
        return dto;
    }

    private String generateAccountNumber() {
        // Generate a unique account number like C00123456
        int randomNum = ThreadLocalRandom.current().nextInt(100000, 999999);
        return "C" + String.format("%06d", randomNum);
    }

    public PaginationResponse<CompteDTO> getComptesPaginated(Pageable pageable, String type, UUID userid) {
        Page<Compte> comptePage = compteRepository.findComptesWithFilters(type, userid, pageable);
        List<CompteDTO> content = comptePage.getContent()
                .stream()
                .map(compte -> {
                    CompteDTO dto = modelMapper.map(compte, CompteDTO.class);
                    if (compte.getUser() != null) {
                        dto.setUserName(compte.getUser().getNom());
                    }
                    return dto;
                })
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

    public CompteDTO blockCompte(UUID id, String motif) {
        return compteRepository.findById(id)
                .map(compte -> {
                    compte.setStatut("BLOQUE");
                    compte.setMotifBlocage(motif);
                    Compte savedCompte = compteRepository.save(compte);
                    return modelMapper.map(savedCompte, CompteDTO.class);
                })
                .orElseThrow(() -> new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage()));
    }

    public CompteDTO unblockCompte(UUID id) {
        return compteRepository.findById(id)
                .map(compte -> {
                    compte.setStatut("ACTIF");
                    compte.setMotifBlocage(null);
                    Compte savedCompte = compteRepository.save(compte);
                    return modelMapper.map(savedCompte, CompteDTO.class);
                })
                .orElseThrow(() -> new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage()));
    }

    public CompteDTO closeCompte(UUID id) {
        return compteRepository.findById(id)
                .map(compte -> {
                    if (compte.getSolde().compareTo(BigDecimal.ZERO) > 0) {
                        throw new CompteException("Cannot close account with balance", "Impossible de fermer un compte avec un solde positif");
                    }
                    compte.setStatut("FERME");
                    Compte savedCompte = compteRepository.save(compte);
                    return modelMapper.map(savedCompte, CompteDTO.class);
                })
                .orElseThrow(() -> new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage()));
    }

    public List<TransactionDTO> getTransactionHistory(UUID compteId) {
        return compteRepository.findById(compteId)
                .map(compte -> compte.getTransactions()
                        .stream()
                        .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage()));
    }

    public CompteDTO updateBalance(UUID id, Double amount, String operationType, String description) {
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

                    // Create transaction record
                    Transaction transaction = new Transaction();
                    transaction.setId(UUID.randomUUID());
                    transaction.setMontant(changeAmount);
                    transaction.setType(operationType);
                    transaction.setDate(java.time.LocalDateTime.now().toString());
                    transaction.setCompteId(id);

                    compte.getTransactions().add(transaction);

                    Compte savedCompte = compteRepository.save(compte);
                    return modelMapper.map(savedCompte, CompteDTO.class);
                })
                .orElseThrow(() -> new CompteException(ErrorMessages.COMPTE_NOT_FOUND.name(), ErrorMessages.COMPTE_NOT_FOUND.getMessage()));
    }
}