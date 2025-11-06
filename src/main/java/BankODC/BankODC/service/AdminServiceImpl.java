package BankODC.BankODC.service;

import BankODC.BankODC.entity.Admin;
import BankODC.BankODC.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<Admin> getAdminById(UUID id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional<Admin> getAdminByUserid(UUID userid) {
        return adminRepository.findById(userid);
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(UUID id) {
        adminRepository.deleteById(id);
    }
}