package BankODC.BankODC.controller;

import BankODC.BankODC.constants.ApiResponse;
import BankODC.BankODC.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private MessageUtil messageUtil;

    @GetMapping("/")
    public String home() {
        return "redirect:/swagger-ui/index.html";
    }

    // Exemple d'utilisation des messages internationalisés
    @GetMapping("/api/messages/demo")
    public ResponseEntity<ApiResponse<Map<String, String>>> getDemoMessages(
            @RequestHeader(value = "Accept-Language", defaultValue = "fr") String acceptLanguage) {

        Map<String, String> messages = new HashMap<>();

        // Messages d'erreur
        messages.put("error_operation_failed", messageUtil.getErrorMessage("operation_failed"));
        messages.put("error_invalid_data", messageUtil.getErrorMessage("invalid_data"));
        messages.put("error_client_not_found", messageUtil.getErrorMessage("client_not_found"));

        // Messages de succès
        messages.put("success_operation_successful", messageUtil.getSuccessMessage("operation_successful"));
        messages.put("success_client_created", messageUtil.getSuccessMessage("client_created_successfully"));
        messages.put("success_data_retrieved", messageUtil.getSuccessMessage("data_retrieved_successfully"));

        // Message avec arguments (exemple)
        String welcomeMessage = messageUtil.getMessage("success.operation_successful");
        messages.put("welcome_message", welcomeMessage);

        String responseMessage = messageUtil.getSuccessMessage("data_retrieved_successfully");

        return ResponseEntity.ok(ApiResponse.success(responseMessage, messages));
    }
}