package BankODC.BankODC.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public class CompteDTO {

    @Schema(description = "Identifiant unique du compte", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Identifiant de l'utilisateur propriétaire du compte", example = "123e4567-e89b-12d3-a456-426614174001")
    @NotNull(message = "L'ID utilisateur est obligatoire")
    private UUID userid;

    @Schema(description = "Type de compte (ex: COURANT, EPARGNE)", example = "COURANT")
    @NotBlank(message = "Le type de compte est obligatoire")
    @Size(max = 20, message = "Le type ne peut pas dépasser 20 caractères")
    private String type;

    @Schema(description = "Solde du compte", example = "1500.00")
    @NotNull(message = "Le solde est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le solde doit être positif")
    private BigDecimal solde;

    @Schema(description = "Numéro du compte bancaire", example = "FR1234567890123456789012345")
    @Size(max = 50, message = "Le numéro de compte ne peut pas dépasser 50 caractères")
    private String numero;

    // Constructeurs
    public CompteDTO() {}

    public CompteDTO(UUID id, UUID userid, String type, BigDecimal solde, String numero) {
        this.id = id;
        this.userid = userid;
        this.type = type;
        this.solde = solde;
        this.numero = numero;
    }

    // Getters et Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserid() { return userid; }
    public void setUserid(UUID userid) { this.userid = userid; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) { this.solde = solde; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
}