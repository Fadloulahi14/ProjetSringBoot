package BankODC.BankODC.repository;

import BankODC.BankODC.entity.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompteRepository extends JpaRepository<Compte, UUID> {

    @Override
    Optional<Compte> findById(UUID uuid);

    @Query("SELECT c FROM Compte c LEFT JOIN FETCH c.user WHERE (:type IS NULL OR c.type = :type) AND (:userid IS NULL OR c.user.id = :userid)")
    Page<Compte> findComptesWithFilters(@Param("type") String type, @Param("userid") UUID userid, Pageable pageable);

    Optional<Compte> findByNumeroCompte(String numeroCompte);
}
