package BankODC.BankODC.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "DTO for admin response data")
public class AdminResponse {

    @Schema(description = "Identifiant unique de l'administrateur", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Nom de l'administrateur", example = "Dupont")
    private String nom;

    @Schema(description = "Prénom de l'administrateur", example = "Jean")
    private String prenom;

    @Schema(description = "Adresse email de l'administrateur", example = "jean.dupont@bankodc.com")
    private String email;

    @Schema(description = "Numéro de téléphone de l'administrateur", example = "+221 77 123 45 67")
    private String telephone;

   
    public AdminResponse() {}

    public AdminResponse(UUID id, String nom, String prenom, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}