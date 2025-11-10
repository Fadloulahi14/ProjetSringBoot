package BankODC.BankODC.dto.request;

import BankODC.BankODC.validator.CompteValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "DTO for account creation/update requests")
public class CompteRequest {

    @Schema(description = "Type de compte (ex: CHEQUE, EPARGNE)", example = "epargne")
    private String type;

    @Schema(description = "Solde du compte", example = "1250000")
    private BigDecimal solde;

    @Schema(description = "Devise du compte", example = "FCFA")
    private String devise;

    // Constructors
    public CompteRequest() {}

    public CompteRequest(String type, BigDecimal solde, String devise) {
        this.type = type;
        this.solde = solde;
        this.devise = devise;
        CompteValidator.validateCompteRequest(this);
    }

    public String getType() { return type; }
    public void setType(String type) {
        this.type = type;
        validate();
    }

    public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) {
        this.solde = solde;
        validate();
    }

    public String getDevise() { return devise; }
    public void setDevise(String devise) {
        this.devise = devise;
        validate();
    }

    // Validation method
    private void validate() {
        if (type != null && solde != null) {
            CompteValidator.validateCompteRequest(this);
        }
    }
}