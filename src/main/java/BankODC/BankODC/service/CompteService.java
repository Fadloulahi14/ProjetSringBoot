package BankODC.BankODC.service;

import BankODC.BankODC.dto.CompteDTO;
import BankODC.BankODC.constants.ErrorMessages;
import BankODC.BankODC.entity.Compte;
import BankODC.BankODC.repository.CompteRepository;
import BankODC.BankODC.exception.CompteException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompteService implements ICompteService {

    @Autowired
    private CompteRepository compteRepository;

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
        if (compte.getNumero() == null || compte.getNumero().trim().isEmpty()) {
            throw new CompteException(ErrorMessages.REQUIRED_FIELD_MISSING.name(), ErrorMessages.REQUIRED_FIELD_MISSING.getMessage());
        }

        if (compte.getSolde().compareTo(BigDecimal.ZERO) < 0) {
            throw new CompteException(ErrorMessages.COMPTE_NEGATIVE_BALANCE.name(), ErrorMessages.COMPTE_NEGATIVE_BALANCE.getMessage());
        }

        if (compte.getType() == null) {
            throw new CompteException(ErrorMessages.COMPTE_INVALID_TYPE.name(), ErrorMessages.COMPTE_INVALID_TYPE.getMessage());
        }
    }

    private void validateDeletion(Compte compte) {
        if (compte.getSolde().compareTo(BigDecimal.ZERO) > 0) {
            throw new CompteException(ErrorMessages.COMPTE_CANNOT_DELETE_WITH_BALANCE.name(), ErrorMessages.COMPTE_CANNOT_DELETE_WITH_BALANCE.getMessage());
        }
    }

    private void updateCompteFields(Compte compte, Compte compteDetails) {
        compte.setNumero(compteDetails.getNumero());
        compte.setSolde(compteDetails.getSolde());
        compte.setType(compteDetails.getType());
    }
}