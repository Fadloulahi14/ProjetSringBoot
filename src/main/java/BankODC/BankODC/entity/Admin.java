package BankODC.BankODC.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMIN")  // Pour différencier les types d'utilisateurs dans la table
public class Admin extends User {

    @Column(nullable = false)
    private String role = "ADMIN";

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}