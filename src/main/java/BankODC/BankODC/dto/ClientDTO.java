package BankODC.BankODC.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public class ClientDTO {

    @Schema(description = "Identifiant unique du client", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Identifiant de l'utilisateur associé", example = "123e4567-e89b-12d3-a456-426614174001")
    @NotNull(message = "L'ID utilisateur est obligatoire")
    private UUID userId;

    @Schema(description = "Nom du client", example = "Dupont")
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String nom;

    @Schema(description = "Prénom du client", example = "Marie")
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String prenom;

    @Schema(description = "Adresse email du client", example = "marie.dupont@email.com")
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @Schema(description = "Numéro de téléphone du client", example = "+221 77 123 45 67")
    @Size(max = 20, message = "Le téléphone ne peut pas dépasser 20 caractères")
    private String telephone;

    @Schema(description = "Adresse du client", example = "123 Rue de la Banque, Dakar")
    @Size(max = 255, message = "L'adresse ne peut pas dépasser 255 caractères")
    private String adresse;

    @Schema(description = "Date de naissance du client (format YYYY-MM-DD)", example = "1990-01-15")
    private String dateNaissance;

   
    public ClientDTO() {}

    public ClientDTO(UUID id, UUID userId, String nom, String prenom, String email,
                    String telephone, String adresse, String dateNaissance) {
        this.id = id;
        this.userId = userId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
    }

    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }
}