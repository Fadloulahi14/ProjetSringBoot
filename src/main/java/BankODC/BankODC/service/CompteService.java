package BankODC.BankODC.service;

import BankODC.BankODC.entity.Compte;
import BankODC.BankODC.repository.CompteRepository;
import BankODC.BankODC.exception.CompteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;

    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    public Optional<Compte> getCompteById(UUID id) {
        if (id == null) {
            throw new CompteException("ID_NULL", "L'identifiant du compte ne peut pas être null");
        }
        return compteRepository.findById(id);
    }

    public Compte saveCompte(Compte compte) {
        if (compte == null) {
            throw new CompteException("COMPTE_NULL", "Le compte ne peut pas être null");
        }
        

        if (compte.getSolde() == null) {
            compte.setSolde(BigDecimal.valueOf(0.0));
        }
        
        validateCompte(compte);
        return compteRepository.save(compte);
    }

    public Compte updateCompte(UUID id, Compte compteDetails) {
        if (id == null || compteDetails == null) {
            throw new CompteException("DONNEES_NULL", "L'id ou les détails du compte sont null");
        }

        return compteRepository.findById(id)
            .map(compte -> {
                updateCompteFields(compte, compteDetails);
                validateCompte(compte);
                return compteRepository.save(compte);
            })
            .orElseThrow(() -> new CompteException("COMPTE_NON_TROUVE", "Compte non trouvé avec l'id: " + id));
    }

    public boolean deleteCompte(UUID id) {
        if (id == null) {
            throw new CompteException("ID_NULL", "L'identifiant du compte ne peut pas être null");
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
        if (compte.getNumero() == null || compte.getNumero().trim().isEmpty()) {
            throw new CompteException("NUMERO_INVALID", "Le numéro de compte est obligatoire");
        }


        if (compte.getSolde().compareTo(BigDecimal.ZERO) < 0) {
            throw new CompteException("SOLDE_NEGATIF", "Le solde ne peut pas être négatif");
        }

        if (compte.getType() == null) {
            throw new CompteException("TYPE_NULL", "Le type de compte est obligatoire");
        }
    }

    private void validateDeletion(Compte compte) {

        if (compte.getSolde().compareTo(BigDecimal.ZERO) > 0) {
            throw new CompteException("SOLDE_POSITIF", "Impossible de supprimer un compte avec un solde positif");
        }
    }

    private void updateCompteFields(Compte compte, Compte compteDetails) {
        compte.setNumero(compteDetails.getNumero());
        compte.setSolde(compteDetails.getSolde());
        compte.setType(compteDetails.getType());
    }
}