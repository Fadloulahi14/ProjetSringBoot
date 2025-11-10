package BankODC.BankODC.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO for transaction response data")
public class TransactionResponse {

    @Schema(description = "Identifiant unique de la transaction", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Identifiant du compte associé à la transaction", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID compteId;

    @Schema(description = "Type de transaction (ex: DEPOT, RETRAIT, VIREMENT)", example = "DEPOT")
    private String type;

    @Schema(description = "Montant de la transaction", example = "500.00")
    private BigDecimal montant;

    @Schema(description = "Date de la transaction (format YYYY-MM-DD HH:mm:ss)", example = "2023-10-15 14:30:00")
    private String date;

    // Constructors
    public TransactionResponse() {}

    public TransactionResponse(UUID id, UUID compteId, String type, BigDecimal montant, String date) {
        this.id = id;
        this.compteId = compteId;
        this.type = type;
        this.montant = montant;
        this.date = date;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getCompteId() { return compteId; }
    public void setCompteId(UUID compteId) { this.compteId = compteId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}