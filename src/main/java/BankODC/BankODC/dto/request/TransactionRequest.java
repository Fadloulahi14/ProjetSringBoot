package BankODC.BankODC.dto.request;

import BankODC.BankODC.validator.TransactionValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO for transaction creation/update requests")
public class TransactionRequest {

    @Schema(description = "Identifiant du compte associé à la transaction", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID compteId;

    @Schema(description = "Type de transaction (ex: DEPOT, RETRAIT, VIREMENT)", example = "DEPOT")
    private String type;

    @Schema(description = "Montant de la transaction", example = "500.00")
    private BigDecimal montant;

    // Constructors
    public TransactionRequest() {}

    public TransactionRequest(UUID compteId, String type, BigDecimal montant) {
        this.compteId = compteId;
        this.type = type;
        this.montant = montant;
        TransactionValidator.validateTransactionRequest(this);
    }

    // Getters and Setters with validation
    public UUID getCompteId() { return compteId; }
    public void setCompteId(UUID compteId) {
        this.compteId = compteId;
        validate();
    }

    public String getType() { return type; }
    public void setType(String type) {
        this.type = type;
        validate();
    }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
        validate();
    }

    // Validation method
    private void validate() {
        if (compteId != null && type != null && montant != null) {
            TransactionValidator.validateTransactionRequest(this);
        }
    }
}