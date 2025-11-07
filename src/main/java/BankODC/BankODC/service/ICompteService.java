package BankODC.BankODC.service;

import BankODC.BankODC.dto.CompteCreateDTO;
import BankODC.BankODC.dto.CompteDTO;
import BankODC.BankODC.dto.PaginationResponse;
import BankODC.BankODC.dto.TransactionDTO;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICompteService {
    List<CompteDTO> getAllComptes();
    Optional<CompteDTO> getCompteById(UUID id);
    CompteDTO saveCompte(CompteDTO compteDTO);
    CompteDTO createCompteWithClient(CompteCreateDTO compteCreateDTO);
    CompteDTO updateCompte(UUID id, CompteDTO compteDTO);
    boolean deleteCompte(UUID id);
    PaginationResponse<CompteDTO> getComptesPaginated(Pageable pageable, String type, UUID userid);
    CompteDTO blockCompte(UUID id, String motif);
    CompteDTO unblockCompte(UUID id);
    CompteDTO closeCompte(UUID id);
    List<TransactionDTO> getTransactionHistory(UUID compteId);
    CompteDTO updateBalance(UUID id, Double amount, String operationType, String description);
}