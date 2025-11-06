package BankODC.BankODC.repository;

import BankODC.BankODC.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    // JpaRepository already provides all needed methods:
    // findById(), findAll(), save(), deleteById()
}
