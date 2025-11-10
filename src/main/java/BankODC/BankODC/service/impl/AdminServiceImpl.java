package BankODC.BankODC.service.impl;

import BankODC.BankODC.constants.ErrorMessages;
import BankODC.BankODC.dto.AdminCreateDTO;
import BankODC.BankODC.dto.response.AdminResponse;
import BankODC.BankODC.dto.request.AdminRequest;
import BankODC.BankODC.validator.AdminValidator;
import BankODC.BankODC.entity.Admin;
import BankODC.BankODC.repository.AdminRepository;
import BankODC.BankODC.exception.AdminException;
import BankODC.BankODC.service.interfaces.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

   
    public List<AdminResponse> getAllAdmins() {
        return getAllAdminsResponse();
    }

    public Optional<AdminResponse> getAdminById(UUID id) {
        return getAdminByIdResponse(id);
    }

    public AdminResponse createAdmin(AdminCreateDTO adminCreateDTO) {
        if (adminCreateDTO == null) {
            throw new AdminException(ErrorMessages.INVALID_DATA.name(), ErrorMessages.INVALID_DATA.getMessage());
        }

        Admin admin = modelMapper.map(adminCreateDTO, Admin.class);
        AdminValidator.validateAdminEntity(admin);


        admin.setId(UUID.randomUUID());


        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

       
        entityManager.persist(admin);
        return new AdminResponse(admin.getId(), admin.getNom(), admin.getPrenom(), admin.getEmail(), admin.getTelephone());
    }

    public AdminResponse updateAdmin(UUID id, AdminResponse adminDetails) {
        return updateAdminFromRequest(id, new AdminRequest(adminDetails.getNom(), adminDetails.getPrenom(), adminDetails.getEmail(), adminDetails.getTelephone()));
    }

  
    public List<AdminResponse> getAllAdminsResponse() {
        return adminRepository.findAll()
                .stream()
                .map(admin -> new AdminResponse(admin.getId(), admin.getNom(), admin.getPrenom(), admin.getEmail(), admin.getTelephone()))
                .collect(Collectors.toList());
    }

    public Optional<AdminResponse> getAdminByIdResponse(UUID id) {
        if (id == null) {
            throw new AdminException(ErrorMessages.INVALID_DATA.name(), ErrorMessages.INVALID_DATA.getMessage());
        }
        return adminRepository.findById(id)
                .map(admin -> new AdminResponse(admin.getId(), admin.getNom(), admin.getPrenom(), admin.getEmail(), admin.getTelephone()));
    }

    @Override
    public AdminResponse updateAdminFromRequest(UUID id, AdminRequest adminRequest) {
        if (id == null || adminRequest == null) {
            throw new AdminException(ErrorMessages.INVALID_DATA.name(), ErrorMessages.INVALID_DATA.getMessage());
        }

        return adminRepository.findById(id)
            .map(admin -> {
                
                admin.setNom(adminRequest.getNom());
                admin.setPrenom(adminRequest.getPrenom());
                admin.setEmail(adminRequest.getEmail());
                admin.setTelephone(adminRequest.getTelephone());

                AdminValidator.validateAdminEntity(admin);
                Admin savedAdmin = adminRepository.save(admin);
                return new AdminResponse(savedAdmin.getId(), savedAdmin.getNom(), savedAdmin.getPrenom(), savedAdmin.getEmail(), savedAdmin.getTelephone());
            })
            .orElseThrow(() -> new AdminException(ErrorMessages.ADMIN_NOT_FOUND.name(), ErrorMessages.ADMIN_NOT_FOUND.getMessage()));
    }

    // Validation methods removed - now handled by validators

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