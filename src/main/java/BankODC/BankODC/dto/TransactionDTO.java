package BankODC.BankODC.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public class TransactionDTO {

    @Schema(description = "Identifiant unique de la transaction", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Identifiant du compte associé à la transaction", example = "123e4567-e89b-12d3-a456-426614174001")
    @NotNull(message = "L'ID du compte est obligatoire")
    private UUID compteId;

    @Schema(description = "Type de transaction (ex: DEPOT, RETRAIT, VIREMENT)", example = "DEPOT")
    @NotBlank(message = "Le type de transaction est obligatoire")
    @Size(max = 20, message = "Le type ne peut pas dépasser 20 caractères")
    private String type;

    @Schema(description = "Montant de la transaction", example = "500.00")
    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    private BigDecimal montant;

    @Schema(description = "Date de la transaction (format YYYY-MM-DD HH:mm:ss)", example = "2023-10-15 14:30:00")
    private String date;

    
    public TransactionDTO() {}

    public TransactionDTO(UUID id, UUID compteId, String type, BigDecimal montant, String date) {
        this.id = id;
        this.compteId = compteId;
        this.type = type;
        this.montant = montant;
        this.date = date;
    }

    // Getters et Setters
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