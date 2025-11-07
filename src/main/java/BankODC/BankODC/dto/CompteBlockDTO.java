package BankODC.BankODC.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO for blocking/unblocking an account")
public class CompteBlockDTO {

    @Schema(description = "Reason for blocking/unblocking the account", example = "Suspicious activity")
    @NotBlank(message = "Le motif est obligatoire")
    private String motif;

    // Constructors
    public CompteBlockDTO() {}

    public CompteBlockDTO(String motif) {
        this.motif = motif;
    }

    // Getters and Setters
    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}