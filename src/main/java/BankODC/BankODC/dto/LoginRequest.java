package BankODC.BankODC.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Login request payload")
public class LoginRequest {

    @Schema(description = "Username for authentication", example = "admin")
    private String username;

    @Schema(description = "Password for authentication", example = "password")
    private String password;

    // Constructors
    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}