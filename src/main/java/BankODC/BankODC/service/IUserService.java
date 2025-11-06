package BankODC.BankODC.service;

import BankODC.BankODC.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<User> getAllUsers();
    Optional<User> getUserById(UUID id);
    User saveUser(User user);
    void deleteUser(UUID id);
    boolean existsByEmail(String email);
}