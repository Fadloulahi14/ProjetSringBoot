# Guide d'Implémentation de l'Internationalisation (i18n) dans Spring Boot

## 🎯 Objectif

Ce guide explique comment implémenter un système d'internationalisation complet dans une application Spring Boot, permettant de retourner des messages traduits selon la langue demandée par l'utilisateur.

## 📋 Problème Résolu

**Avant** : Les APIs retournaient des clés brutes comme `"success.login_successful"`
**Après** : Les APIs retournent des messages traduits comme `"Authentification réussie"` ou `"Login successful"`

## 🛠️ Technologies Utilisées

- **Spring Boot 3.5.7**
- **Jackson DataFormat YAML** pour lire les fichiers YAML
- **Spring Context** pour la gestion des messages
- **Java 17+**

## 📁 Structure des Fichiers

```
src/main/resources/
├── i18n/
│   ├── messages_fr.yml    # Messages en français
│   └── messages_en.yml    # Messages en anglais
└── application.yml        # Configuration Spring Boot
```

## 🔧 Configuration Maven (pom.xml)

Ajoutez ces dépendances dans votre `pom.xml` :

```xml
<!-- Support YAML pour Jackson -->
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-yaml</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
```

## 📝 Fichiers de Messages

### messages_fr.yml
```yaml
error:
  admin_invalid_credentials: "Identifiants invalides"
  client_not_found: "Client non trouvé"
  operation_failed: "L'opération a échoué"

success:
  login_successful: "Authentification réussie"
  client_created_successfully: "Client créé avec succès"
  operation_successful: "Opération réalisée avec succès"
```

### messages_en.yml
```yaml
error:
  admin_invalid_credentials: "Invalid credentials"
  client_not_found: "Client not found"
  operation_failed: "Operation failed"

success:
  login_successful: "Login successful"
  client_created_successfully: "Client created successfully"
  operation_successful: "Operation completed successfully"
```

## ⚙️ Configuration Spring Boot

### 1. MessageConfig.java

Créez le fichier `src/main/java/BankODC/BankODC/config/MessageConfig.java` :

```java
package BankODC.BankODC.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.StaticMessageSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MessageConfig {

    @Bean
    public StaticMessageSource messageSource() {
        StaticMessageSource messageSource = new StaticMessageSource();

        // Charger les messages français
        loadYamlMessages("i18n/messages_fr.yml", java.util.Locale.FRENCH, messageSource);

        // Charger les messages anglais
        loadYamlMessages("i18n/messages_en.yml", java.util.Locale.ENGLISH, messageSource);

        return messageSource;
    }

    private void loadYamlMessages(String resourcePath, java.util.Locale locale, StaticMessageSource messageSource) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            Properties props = new Properties();

            try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
                if (is != null) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> yamlMap = mapper.readValue(is, Map.class);
                    flattenYaml("", yamlMap, props);

                    // Ajouter tous les messages au MessageSource
                    for (String key : props.stringPropertyNames()) {
                        messageSource.addMessage(key, locale, props.getProperty(key));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des messages YAML depuis " + resourcePath + ": " + e.getMessage());
        }
    }

    private void flattenYaml(String prefix, Map<String, Object> map, Properties props) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                flattenYaml(key, nestedMap, props);
            } else {
                props.setProperty(key, value.toString());
            }
        }
    }
}
```

### 2. Configuration de la Locale

Ajoutez dans votre `application.yml` :

```yaml
spring:
  web:
    locale: fr  # Locale par défaut
    locale-resolver: accept-header  # Utilise l'en-tête Accept-Language
```

## 🛠️ Service Utilitaire MessageUtil

Créez `src/main/java/BankODC/BankODC/util/MessageUtil.java` :

```java
package BankODC.BankODC.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtil {

    private final MessageSource messageSource;

    @Autowired
    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Récupère un message en utilisant la locale actuelle
     */
    public String getMessage(String key) {
        return getMessage(key, null, LocaleContextHolder.getLocale());
    }

    /**
     * Récupère un message avec des arguments
     */
    public String getMessage(String key, Object[] args) {
        return getMessage(key, args, LocaleContextHolder.getLocale());
    }

    /**
     * Récupère un message pour une locale spécifique
     */
    public String getMessage(String key, Object[] args, Locale locale) {
        try {
            String message = messageSource.getMessage(key, args, locale);
            System.out.println("MessageUtil - Key: " + key + ", Locale: " + locale + ", Message: " + message);
            return message;
        } catch (Exception e) {
            // Essayer avec la locale par défaut si la locale actuelle ne fonctionne pas
            try {
                Locale defaultLocale = Locale.FRENCH;
                String defaultMessage = messageSource.getMessage(key, args, defaultLocale);
                System.out.println("MessageUtil - Using default locale, Message: " + defaultMessage);
                return defaultMessage;
            } catch (Exception e2) {
                System.out.println("MessageUtil - All locales failed, returning key: " + key);
                return key;
            }
        }
    }

    /**
     * Récupère un message d'erreur
     */
    public String getErrorMessage(String errorKey) {
        return getMessage("error." + errorKey);
    }

    /**
     * Récupère un message de succès
     */
    public String getSuccessMessage(String successKey) {
        return getMessage("success." + successKey);
    }
}
```

## 🎮 Utilisation dans les Contrôleurs

### AuthController.java

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private MessageUtil messageUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        try {
            // Logique d'authentification...

            // Succès
            String successMessage = messageUtil.getSuccessMessage("login_successful");
            return ResponseEntity.ok(ApiResponse.success(successMessage, loginResponse));

        } catch (Exception e) {
            // Erreur
            String errorMessage = messageUtil.getErrorMessage("admin_invalid_credentials");
            return ResponseEntity.ok(ApiResponse.error(errorMessage, null));
        }
    }
}
```

## 🧪 Tests

### Test en français (par défaut)
```bash
curl -X POST http://localhost:8090/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"fadil@bankodc.com","password":"password"}'
```

**Réponse attendue :**
```json
{
  "success": true,
  "message": "Authentification réussie",
  "data": { ... },
  "timestamp": "..."
}
```

### Test en anglais
```bash
curl -X POST http://localhost:8090/api/auth/login \
  -H "Accept-Language: en" \
  -H "Content-Type: application/json" \
  -d '{"username":"fadil@bankodc.com","password":"password"}'
```

**Réponse attendue :**
```json
{
  "success": true,
  "message": "Login successful",
  "data": { ... },
  "timestamp": "..."
}
```

## 🔍 Dépannage

### Problème : Messages non traduits
**Solution :** Vérifiez que :
1. Les fichiers YAML sont dans `src/main/resources/i18n/`
2. Les dépendances Maven sont ajoutées
3. Le MessageConfig est bien chargé au démarrage

### Problème : Locale non détectée
**Solution :** Vérifiez l'en-tête `Accept-Language` dans vos requêtes HTTP.

### Logs de débogage
Les logs montrent le processus de résolution des messages :
```
MessageUtil - Key: success.login_successful, Locale: fr, Message: Authentification réussie
```

## 🎉 Résultat Final

✅ **Messages internationalisés** : Plus de clés brutes
✅ **Support multi-langue** : Français et anglais
✅ **Fallback automatique** : Français par défaut
✅ **Facile à étendre** : Ajouter de nouvelles langues facilement
✅ **Centralisé** : Tous les messages dans des fichiers YAML

## 🚀 Utilisation Avancée

### Ajouter une nouvelle langue
1. Créer `messages_es.yml` pour l'espagnol
2. Ajouter le chargement dans `MessageConfig.java`
3. Utiliser `Accept-Language: es` dans les requêtes

### Messages paramétrés
```yaml
success:
  welcome_user: "Bienvenue {0} !"
```

```java
String message = messageUtil.getMessage("success.welcome_user", new Object[]{"John"});
```

**Résultat :** "Bienvenue John !"

---

**Auteur :** Kilo Code
**Date :** 9 novembre 2025
**Version :** 1.0