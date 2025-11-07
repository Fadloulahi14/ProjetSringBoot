package BankODC.BankODC.controller;

import BankODC.BankODC.constants.ApiResponse;
import BankODC.BankODC.constants.SuccessMessages;
import BankODC.BankODC.dto.LoginRequest;
import BankODC.BankODC.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API d'authentification JWT")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "Authentification utilisateur", description = "Génère un token JWT pour l'utilisateur (mock)")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Token généré avec succès")
    })
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody LoginRequest loginRequest) {
        String role = "admin".equals(loginRequest.getUsername()) ? "ADMIN" : "USER";
        String token = jwtUtil.generateToken(loginRequest.getUsername(), role);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", role);

        return ResponseEntity.ok(ApiResponse.success(SuccessMessages.LOGIN_SUCCESSFUL, response));
    }
}