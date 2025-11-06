package BankODC.BankODC.service;

import BankODC.BankODC.dto.AdminCreateDTO;
import BankODC.BankODC.dto.AdminDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAdminService {
    List<AdminDTO> getAllAdmins();
    Optional<AdminDTO> getAdminById(UUID id);
    AdminDTO createAdmin(AdminCreateDTO adminCreateDTO);
    AdminDTO updateAdmin(UUID id, AdminDTO adminDTO);
    boolean deleteAdmin(UUID id);
}