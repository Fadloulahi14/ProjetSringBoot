package BankODC.BankODC.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO for updating account balance")
public class BalanceUpdateDTO {

    @Schema(description = "Amount to add/subtract from balance", example = "50000")
    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être positif")
    private Double amount;

    @Schema(description = "Operation type (DEPOSIT, WITHDRAWAL)", example = "DEPOSIT")
    @NotNull(message = "Le type d'opération est obligatoire")
    private String operationType;

    @Schema(description = "Description of the operation", example = "Dépôt en espèces")
    private String description;

    // Constructors
    public BalanceUpdateDTO() {}

    public BalanceUpdateDTO(Double amount, String operationType, String description) {
        this.amount = amount;
        this.operationType = operationType;
        this.description = description;
    }

    // Getters and Setters
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}