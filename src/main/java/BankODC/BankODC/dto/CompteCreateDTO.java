package BankODC.BankODC.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO for creating a new account with client information")
public class CompteCreateDTO {

    @Schema(description = "Account type (ex: CHEQUE, EPARGNE)", example = "CHEQUE")
    @NotBlank(message = "Le type de compte est obligatoire")
    @Size(max = 20, message = "Le type ne peut pas dépasser 20 caractères")
    private String type;

    @Schema(description = "Initial balance for the account", example = "500000")
    private BigDecimal soldeInitial;

    @Schema(description = "Currency (default: FCFA)", example = "FCFA")
    private String devise = "FCFA";

    @Schema(description = "Current balance (can be different from initial)", example = "10000")
    @NotNull(message = "Le solde est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le solde doit être positif")
    private BigDecimal solde;

    // Client information
    @Schema(description = "Client ID (null for new client)", example = "null")
    private UUID id;

    @Schema(description = "Client full name", example = "Hawa BB Wane")
    @NotBlank(message = "Le nom du titulaire est obligatoire")
    private String titulaire;

    @Schema(description = "Client email", example = "cheikh.sy@example.com")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @Schema(description = "Client phone number", example = "+221771234567")
    private String telephone;

    @Schema(description = "Client address", example = "Dakar, Sénégal")
    private String adresse;

    // Constructors
    public CompteCreateDTO() {}

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(BigDecimal soldeInitial) {
        this.soldeInitial = soldeInitial;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}