package BankODC.BankODC.dto.request;

import BankODC.BankODC.validator.ClientValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "DTO for client creation/update requests")
public class ClientRequest {

    @Schema(description = "Identifiant de l'utilisateur associé", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID userId;

    @Schema(description = "Nom du client", example = "Dupont")
    private String nom;

    @Schema(description = "Prénom du client", example = "Marie")
    private String prenom;

    @Schema(description = "Adresse email du client", example = "marie.dupont@email.com")
    private String email;

    @Schema(description = "Numéro de téléphone du client", example = "+221 77 123 45 67")
    private String telephone;

    @Schema(description = "Adresse du client", example = "123 Rue de la Banque, Dakar")
    private String adresse;

    @Schema(description = "Date de naissance du client (format YYYY-MM-DD)", example = "1990-01-15")
    private String dateNaissance;

    // Constructors
    public ClientRequest() {}

    public ClientRequest(UUID userId, String nom, String prenom, String email,
                        String telephone, String adresse, String dateNaissance) {
        this.userId = userId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        ClientValidator.validateClientRequest(this);
    }

    // Getters and Setters with validation
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) {
        this.userId = userId;
        validate();
    }

    public String getNom() { return nom; }
    public void setNom(String nom) {
        this.nom = nom;
        validate();
    }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
        validate();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
        validate();
    }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
        validate();
    }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
        validate();
    }

    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
        validate();
    }

    // Validation method
    private void validate() {
        if (nom != null && email != null) {
            ClientValidator.validateClientRequest(this);
        }
    }
}