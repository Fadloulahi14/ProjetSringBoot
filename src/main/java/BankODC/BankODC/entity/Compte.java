// java
package BankODC.BankODC.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

@Entity
@Table(name = "comptes")
public class Compte {

    @Id
    private UUID id;

    private UUID userid;

    @ManyToOne
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private User user;

    private String type;

    private BigDecimal solde = BigDecimal.ZERO;

    private String numero;

    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getUserid() {
        return userid;
    }
    public void setUserid(UUID userid) {
        this.userid = userid;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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
    public List<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}