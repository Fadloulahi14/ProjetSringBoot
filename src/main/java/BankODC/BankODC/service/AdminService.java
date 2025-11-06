package BankODC.BankODC.service;

import BankODC.BankODC.entity.Admin;
import BankODC.BankODC.repository.AdminRepository;
import BankODC.BankODC.exception.AdminException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(UUID id) {
        if (id == null) {
            throw new AdminException("ID_NULL", "L'identifiant de l'admin ne peut pas être null");
        }
        return adminRepository.findById(id);
    }

    public Admin saveAdmin(Admin admin) {
        if (admin == null) {
            throw new AdminException("ADMIN_NULL", "L'admin ne peut pas être null");
        }
        validateAdmin(admin);
       
        if (admin.getId() == null) {
            admin.setId(UUID.randomUUID());
        }
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(UUID id, Admin adminDetails) {
        if (id == null || adminDetails == null) {
            throw new AdminException("DONNEES_NULL", "L'id ou les détails de l'admin sont null");
        }

        return adminRepository.findById(id)
            .map(admin -> {
                updateAdminFields(admin, adminDetails);
                validateAdmin(admin);
                return adminRepository.save(admin);
            })
            .orElseThrow(() -> new AdminException("ADMIN_NON_TROUVE", "Admin non trouvé avec l'id: " + id));
    }

    private void validateAdmin(Admin admin) {
     
        if (admin.getNom() == null || admin.getNom().trim().isEmpty()) {
            throw new AdminException("NOM_INVALID", "Le nom de l'admin est obligatoire");
        }
        if (admin.getEmail() == null || !admin.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new AdminException("EMAIL_INVALID", "L'email de l'admin est invalide");
        }
        if (admin.getPassword() == null || admin.getPassword().length() < 6) {
            throw new AdminException("PASSWORD_INVALID", "Le mot de passe doit contenir au moins 6 caractères");
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
            throw new AdminException("ID_NULL", "L'identifiant de l'admin ne peut pas être null");
        }

        return adminRepository.findById(id)
            .map(admin -> {
                adminRepository.delete(admin);
                return true;
            })
            .orElse(false);
    }
}