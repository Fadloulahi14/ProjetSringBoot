# BankODC - Application Bancaire Spring Boot

## 📋 Description

BankODC est une application bancaire RESTful développée avec Spring Boot 3.5.7. Elle fournit des services complets de gestion bancaire incluant la gestion des clients, comptes, transactions et administrateurs.

## 🛠️ Technologies Utilisées

- **Framework**: Spring Boot 3.5.7
- **Langage**: Java 17
- **Base de données**: PostgreSQL
- **ORM**: JPA/Hibernate
- **Documentation API**: Swagger/OpenAPI
- **Mapping objet**: ModelMapper
- **Validation**: Jakarta Validation
- **Build Tool**: Maven

## 📁 Architecture

L'application suit une architecture hexagonale avec séparation claire des responsabilités :

```
src/main/java/BankODC/BankODC/
├── config/           # Configuration (Swagger, ModelMapper)
├── constants/        # Constantes (messages d'erreur/succès)
├── controller/       # Contrôleurs REST
├── dto/             # Objets de transfert de données
├── entity/          # Entités JPA
├── exception/       # Gestion des exceptions
├── repository/      # Couches d'accès aux données
└── service/         # Logique métier
```

## 🚀 Fonctionnalités

### Gestion des Clients
- ✅ Création, lecture, mise à jour et suppression des clients
- ✅ Recherche par ID
- ✅ Liste paginée des clients

### Gestion des Comptes
- ✅ Création de comptes (Courant, Épargne)
- ✅ Consultation des soldes
- ✅ Gestion des numéros de compte

### Gestion des Transactions
- ✅ Dépôt, retrait, virement
- ✅ Historique des transactions
- ✅ Suivi des montants et dates

### Gestion des Administrateurs
- ✅ Authentification et autorisation
- ✅ Gestion des rôles utilisateur

### API RESTful
- ✅ Endpoints documentés avec Swagger
- ✅ Réponses standardisées avec ApiResponse
- ✅ Validation des données d'entrée
- ✅ Gestion centralisée des erreurs

## 🔧 Installation et Configuration

### Prérequis
- Java 17
- Maven 3.6+
- PostgreSQL

### Configuration de la Base de Données

1. Créer une base de données PostgreSQL nommée `bankodc`
2. Configurer les paramètres de connexion dans `src/main/resources/application.yml` :

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bankodc
    username: votre_username
    password: votre_password
  jpa:
    hibernate:
      ddl-auto: update
```

### Compilation et Exécution

```bash
# Compiler le projet
mvn clean compile

# Démarrer l'application
mvn spring-boot:run

# Ou avec Java directement
java -jar target/BankODC-0.0.1-SNAPSHOT.jar
```

L'application sera accessible sur `http://localhost:8083`

## 📚 API Documentation

Une fois l'application démarrée, la documentation Swagger est disponible à :
- **Swagger UI**: http://localhost:8083/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8083/v3/api-docs

## 🎯 Endpoints Principaux

### Clients
- `GET /api/clients` - Liste des clients
- `GET /api/clients/{id}` - Détails d'un client
- `POST /api/clients` - Créer un client
- `PUT /api/clients/{id}` - Modifier un client
- `DELETE /api/clients/{id}` - Supprimer un client

### Comptes
- `GET /api/comptes` - Liste des comptes
- `GET /api/comptes/{id}` - Détails d'un compte
- `POST /api/comptes` - Créer un compte

### Transactions
- `GET /api/transactions` - Liste des transactions
- `POST /api/transactions` - Créer une transaction

### Administrateurs
- `GET /api/admins` - Liste des administrateurs
- `POST /api/admins` - Créer un administrateur

### Données de Test
- `POST /api/data/seed` - Initialiser des données de test

## 📦 Structure des Données

### Client
```json
{
  "nom": "DUPONT",
  "prenom": "Marie",
  "email": "marie.dupont@email.com",
  "telephone": "+221 77 234 56 78",
  "adresse": "Dakar, Sénégal",
  "dateNaissance": "1990-05-15"
}
```

### Compte
```json
{
  "type": "COURANT",
  "solde": 2500.00,
  "numero": "FR76 1234 5678 9012 3456 7890 1"
}
```

### Transaction
```json
{
  "type": "DEPOT",
  "montant": 3000.00,
  "date": "2024-01-15"
}
```

## 🔒 Sécurité

- Authentification basée sur les rôles (ADMIN, CLIENT)
- Validation des données d'entrée
- Gestion sécurisée des mots de passe
- Protection contre les attaques courantes

## 🧪 Tests

```bash
# Exécuter les tests
mvn test

# Tests avec couverture
mvn test jacoco:report
```

## 📊 Données de Test

L'application peut être initialisée avec des données de test via l'endpoint `/api/data/seed` :

- **1 Administrateur** : admin@bankodc.com
- **2 Clients** avec comptes et transactions :
  - Marie Dupont (Compte Courant: 2500€)
  - Jean Martin (Compte Épargne: 5000€)

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📝 Licence

Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 👥 Auteurs

- **Développeur**: [Votre Nom]
- **Organisation**: BankODC

## 📞 Support

Pour toute question ou problème :
- Ouvrir une issue sur GitHub
- Contacter l'équipe de développement

---

**Note**: Assurez-vous que PostgreSQL est en cours d'exécution avant de démarrer l'application.