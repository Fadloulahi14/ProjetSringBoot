package BankODC.BankODC.service.interfaces;

import BankODC.BankODC.dto.AdminCreateDTO;
import BankODC.BankODC.dto.response.AdminResponse;
import BankODC.BankODC.dto.request.AdminRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdminService {
    // Legacy methods (backward compatibility)
    List<AdminResponse> getAllAdmins();
    Optional<AdminResponse> getAdminById(UUID id);
    AdminResponse createAdmin(AdminCreateDTO adminCreateDTO);
    AdminResponse updateAdmin(UUID id, AdminResponse adminDTO);
    boolean deleteAdmin(UUID id);

    // New methods for separated DTOs
    AdminResponse updateAdminFromRequest(UUID id, AdminRequest adminRequest);
}