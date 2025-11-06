package BankODC.BankODC.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private UUID id;

    private UUID compteId;
    private String type;
    private java.math.BigDecimal montant;
    private String date;

    @ManyToOne
    @JoinColumn(name = "compteId", insertable = false, updatable = false)
    private Compte compte;

    public Transaction() {
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCompteId() {
        return compteId;
    }

    public void setCompteId(UUID compteId) {
        this.compteId = compteId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public java.math.BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(java.math.BigDecimal montant) {
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
