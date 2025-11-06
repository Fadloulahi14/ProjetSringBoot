package BankODC.BankODC.service;

import BankODC.BankODC.dto.CompteDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICompteService {
    List<CompteDTO> getAllComptes();
    Optional<CompteDTO> getCompteById(UUID id);
    CompteDTO saveCompte(CompteDTO compteDTO);
    CompteDTO updateCompte(UUID id, CompteDTO compteDTO);
    boolean deleteCompte(UUID id);
}