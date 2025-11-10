package BankODC.BankODC.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Schema(description = "DTO for account response data")
public class CompteResponse {

    @Schema(description = "Identifiant unique du compte", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Numéro unique du compte bancaire", example = "C00123456")
    private String numeroCompte;

    @Schema(description = "Nom du titulaire du compte", example = "Amadou Diallo")
    private String titulaire;

    @Schema(description = "Type de compte (ex: CHEQUE, EPARGNE)", example = "epargne")
    private String type;

    @Schema(description = "Solde du compte", example = "1250000")
    private BigDecimal solde;

    @Schema(description = "Devise du compte", example = "FCFA")
    private String devise;

    @Schema(description = "Date de création du compte", example = "2023-03-15T00:00:00Z")
    private LocalDateTime dateCreation;

    @Schema(description = "Statut du compte (ACTIF, BLOQUE, etc.)", example = "bloque")
    private String statut;

    @Schema(description = "Motif de blocage si applicable", example = "Inactivité de 30+ jours")
    private String motifBlocage;

    @Schema(description = "Métadonnées du compte")
    private Map<String, String> metadonnees;

    @Schema(description = "Nom de l'utilisateur propriétaire", example = "Dupont")
    private String userName;

    // Constructors
    public CompteResponse() {}

    public CompteResponse(UUID id, String numeroCompte, String titulaire, String type, BigDecimal solde,
                         String devise, LocalDateTime dateCreation, String statut, String motifBlocage,
                         Map<String, String> metadonnees, String userName) {
        this.id = id;
        this.numeroCompte = numeroCompte;
        this.titulaire = titulaire;
        this.type = type;
        this.solde = solde;
        this.devise = devise;
        this.dateCreation = dateCreation;
        this.statut = statut;
        this.motifBlocage = motifBlocage;
        this.metadonnees = metadonnees;
        this.userName = userName;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNumeroCompte() { return numeroCompte; }
    public void setNumeroCompte(String numeroCompte) { this.numeroCompte = numeroCompte; }

    public String getTitulaire() { return titulaire; }
    public void setTitulaire(String titulaire) { this.titulaire = titulaire; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) { this.solde = solde; }

    public String getDevise() { return devise; }
    public void setDevise(String devise) { this.devise = devise; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getMotifBlocage() { return motifBlocage; }
    public void setMotifBlocage(String motifBlocage) { this.motifBlocage = motifBlocage; }

    public Map<String, String> getMetadonnees() { return metadonnees; }
    public void setMetadonnees(Map<String, String> metadonnees) { this.metadonnees = metadonnees; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}