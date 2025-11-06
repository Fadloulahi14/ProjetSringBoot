package BankODC.BankODC.repository;

import BankODC.BankODC.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompteRepository extends JpaRepository<Compte, UUID> {

    @Override
    Optional<Compte> findById(UUID uuid);
}
