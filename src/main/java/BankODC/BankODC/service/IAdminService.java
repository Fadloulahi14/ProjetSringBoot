package BankODC.BankODC.service;

import BankODC.BankODC.entity.Admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAdminService {
    List<Admin> getAllAdmins();
    Optional<Admin> getAdminById(UUID id);
    Optional<Admin> getAdminByUserid(UUID userid);
    Admin saveAdmin(Admin admin);
    void deleteAdmin(UUID id);
}