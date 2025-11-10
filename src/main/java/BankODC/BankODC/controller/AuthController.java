package BankODC.BankODC.controller;

import BankODC.BankODC.constants.ApiResponse;
import BankODC.BankODC.dto.LoginRequest;
import BankODC.BankODC.entity.User;
import BankODC.BankODC.service.interfaces.UserService;
import BankODC.BankODC.util.JwtUtil;
import BankODC.BankODC.util.MessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API d'authentification JWT")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageUtil messageUtil;

    @PostConstruct
    public void init() {
        messageUtil.testMessages();
    }

    @PostMapping("/login")
    @Operation(summary = "Authentification utilisateur", description = "Authentifie l'utilisateur et génère un token JWT")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Authentification réussie"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Identifiants invalides")
    })
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody LoginRequest loginRequest) {
        

        Optional<User> userOptional = userService.findByEmail(loginRequest.getUsername());
        System.out.println("User found: " + userOptional.isPresent());

        if (userOptional.isEmpty()) {
            
            String errorMessage = messageUtil.getMessage("error.admin_invalid_credentials");
            return ResponseEntity.status(401).body(ApiResponse.error(errorMessage, null));
        }

        User user = userOptional.get();
       

        boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        System.out.println("Password matches: " + passwordMatches);

        if (!passwordMatches) {
           
            String errorMessage = messageUtil.getMessage("error.admin_invalid_credentials");
            return ResponseEntity.status(401).body(ApiResponse.error(errorMessage, null));
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        System.out.println("Token generated: " + token.substring(0, 50) + "...");

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole());

       
        String successMessage = messageUtil.getMessage("success.login_successful");
        return ResponseEntity.ok(ApiResponse.success(successMessage, response));
    }
}