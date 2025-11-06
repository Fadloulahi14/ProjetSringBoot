package BankODC.BankODC.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Schema(description = "Identifiant unique de l'utilisateur", example = "123e4567-e89b-12d3-a456-426614174000")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Schema(description = "Nom de l'utilisateur", example = "Dupont")
    @Column(nullable = false)
    private String nom = "";

    @Schema(description = "Prénom de l'utilisateur", example = "Jean")
    @Column(nullable = false)
    private String prenom = "";

    @Schema(description = "Adresse email de l'utilisateur", example = "jean.dupont@bankodc.com")
    @Column(nullable = false, unique = true)
    private String email = "";

    @Schema(description = "Mot de passe de l'utilisateur", example = "password123")
    @Column(nullable = false)
    private String password = "";

    @Schema(description = "Numéro de téléphone de l'utilisateur", example = "+221 77 123 45 67")
    @Column
    private String telephone;

    @Schema(description = "Rôle de l'utilisateur", example = "USER")
    @Column(nullable = false)
    private String role = "USER";

    // getters and setters...
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}