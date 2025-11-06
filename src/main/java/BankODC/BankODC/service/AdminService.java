package BankODC.BankODC.service;

import BankODC.BankODC.constants.ErrorMessages;
import BankODC.BankODC.dto.AdminDTO;
import BankODC.BankODC.dto.AdminCreateDTO;
import BankODC.BankODC.entity.Admin;
import BankODC.BankODC.repository.AdminRepository;
import BankODC.BankODC.exception.AdminException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(admin -> modelMapper.map(admin, AdminDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<AdminDTO> getAdminById(UUID id) {
        if (id == null) {
            throw new AdminException(ErrorMessages.INVALID_DATA.name(), ErrorMessages.INVALID_DATA.getMessage());
        }
        return adminRepository.findById(id)
                .map(admin -> modelMapper.map(admin, AdminDTO.class));
    }

    public AdminDTO createAdmin(AdminCreateDTO adminCreateDTO) {
        if (adminCreateDTO == null) {
            throw new AdminException(ErrorMessages.INVALID_DATA.name(), ErrorMessages.INVALID_DATA.getMessage());
        }

        Admin admin = modelMapper.map(adminCreateDTO, Admin.class);
        validateAdminForCreation(admin);

        if (admin.getId() == null) {
            admin.setId(UUID.randomUUID());
        }
        Admin savedAdmin = adminRepository.save(admin);
        return modelMapper.map(savedAdmin, AdminDTO.class);
    }

    public AdminDTO updateAdmin(UUID id, AdminDTO adminDetails) {
        if (id == null || adminDetails == null) {
            throw new AdminException(ErrorMessages.INVALID_DATA.name(), ErrorMessages.INVALID_DATA.getMessage());
        }

        return adminRepository.findById(id)
            .map(admin -> {
                updateAdminFields(admin, modelMapper.map(adminDetails, Admin.class));
                validateAdminForUpdate(admin);
                Admin savedAdmin = adminRepository.save(admin);
                return modelMapper.map(savedAdmin, AdminDTO.class);
            })
            .orElseThrow(() -> new AdminException(ErrorMessages.ADMIN_NOT_FOUND.name(), ErrorMessages.ADMIN_NOT_FOUND.getMessage()));
    }

    private void validateAdminForCreation(Admin admin) {
        if (admin.getNom() == null || admin.getNom().trim().isEmpty()) {
            throw new AdminException(ErrorMessages.REQUIRED_FIELD_MISSING.name(), ErrorMessages.REQUIRED_FIELD_MISSING.getMessage());
        }
        if (admin.getEmail() == null || !admin.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new AdminException(ErrorMessages.INVALID_EMAIL_FORMAT.name(), ErrorMessages.INVALID_EMAIL_FORMAT.getMessage());
        }
        if (admin.getPassword() == null || admin.getPassword().length() < 6) {
            throw new AdminException(ErrorMessages.PASSWORD_TOO_SHORT.name(), ErrorMessages.PASSWORD_TOO_SHORT.getMessage());
        }
    }

    private void validateAdminForUpdate(Admin admin) {
        if (admin.getNom() == null || admin.getNom().trim().isEmpty()) {
            throw new AdminException(ErrorMessages.REQUIRED_FIELD_MISSING.name(), ErrorMessages.REQUIRED_FIELD_MISSING.getMessage());
        }
        if (admin.getEmail() == null || !admin.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new AdminException(ErrorMessages.INVALID_EMAIL_FORMAT.name(), ErrorMessages.INVALID_EMAIL_FORMAT.getMessage());
        }
    }

    private void updateAdminFields(Admin admin, Admin adminDetails) {
        admin.setNom(adminDetails.getNom());
        admin.setPrenom(adminDetails.getPrenom());
        admin.setEmail(adminDetails.getEmail());
        admin.setTelephone(adminDetails.getTelephone());
        admin.setRole(adminDetails.getRole());
    }

    public boolean deleteAdmin(UUID id) {
        if (id == null) {
            throw new AdminException(ErrorMessages.INVALID_DATA.name(), ErrorMessages.INVALID_DATA.getMessage());
        }

        return adminRepository.findById(id)
            .map(admin -> {
                adminRepository.delete(admin);
                return true;
            })
            .orElse(false);
    }
}