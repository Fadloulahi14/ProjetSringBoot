// java
package BankODC.BankODC.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "comptes")
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "numero_compte", unique = true)
    private String numeroCompte;

    private String titulaire;

    private String type;

    private BigDecimal solde = BigDecimal.ZERO;

    private String devise = "FCFA";

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();

    private String statut = "ACTIF";

    @Column(name = "motif_blocage")
    private String motifBlocage;

    @ElementCollection
    @CollectionTable(name = "compte_metadonnees", joinColumns = @JoinColumn(name = "compte_id"))
    @MapKeyColumn(name = "cle")
    @Column(name = "valeur")
    private Map<String, String> metadonnees;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getMotifBlocage() {
        return motifBlocage;
    }

    public void setMotifBlocage(String motifBlocage) {
        this.motifBlocage = motifBlocage;
    }

    public Map<String, String> getMetadonnees() {
        return metadonnees;
    }

    public void setMetadonnees(Map<String, String> metadonnees) {
        this.metadonnees = metadonnees;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getUserid() {
        return user != null ? user.getId() : null;
    }

    public void setUserid(UUID userid) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(userid);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}