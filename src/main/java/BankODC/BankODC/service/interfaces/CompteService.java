package BankODC.BankODC.service.interfaces;

import BankODC.BankODC.dto.CompteCreateDTO;
import BankODC.BankODC.dto.PaginationResponse;
import BankODC.BankODC.dto.request.CompteRequest;
import BankODC.BankODC.dto.response.CompteResponse;
import BankODC.BankODC.dto.response.TransactionResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompteService {
    // Legacy methods (backward compatibility)
    List<CompteResponse> getAllComptes();
    Optional<CompteResponse> getCompteById(UUID id);
    CompteResponse saveCompte(CompteResponse compteDTO);
    CompteResponse createCompteWithClient(CompteCreateDTO compteCreateDTO);
    CompteResponse updateCompte(UUID id, CompteResponse compteDTO);
    boolean deleteCompte(UUID id);
    PaginationResponse<CompteResponse> getComptesPaginated(Pageable pageable, String type, UUID userid);
    CompteResponse blockCompte(UUID id, String motif);
    CompteResponse unblockCompte(UUID id);
    CompteResponse closeCompte(UUID id);
    List<TransactionResponse> getTransactionHistory(UUID compteId);
    CompteResponse updateBalance(UUID id, Double amount, String operationType, String description);

    // New methods for separated DTOs
    CompteResponse updateCompteFromRequest(UUID id, CompteRequest compteRequest);
}