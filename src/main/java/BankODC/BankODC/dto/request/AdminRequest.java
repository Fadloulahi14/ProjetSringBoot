package BankODC.BankODC.dto.request;

import BankODC.BankODC.validator.AdminValidator;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for admin creation/update requests")
public class AdminRequest {

    @Schema(description = "Nom de l'administrateur", example = "Dupont")
    private String nom;

    @Schema(description = "Prénom de l'administrateur", example = "Jean")
    private String prenom;

    @Schema(description = "Adresse email de l'administrateur", example = "jean.dupont@bankodc.com")
    private String email;

    @Schema(description = "Numéro de téléphone de l'administrateur", example = "+221 77 123 45 67")
    private String telephone;

    // Constructors
    public AdminRequest() {}

    public AdminRequest(String nom, String prenom, String email, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        AdminValidator.validateAdminRequest(this);
    }

    // Getters and Setters with validation
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

    // Validation method
    private void validate() {
        if (nom != null && email != null) {
            AdminValidator.validateAdminRequest(this);
        }
    }
}