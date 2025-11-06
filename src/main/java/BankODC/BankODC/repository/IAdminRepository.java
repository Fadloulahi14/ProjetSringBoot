package BankODC.BankODC.repository;

import BankODC.BankODC.entity.Admin;

import java.util.Optional;
import java.util.UUID;

public interface IAdminRepository {
    Optional<Admin> findByUserid(UUID userid);
}