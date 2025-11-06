# BankODC

Un système de gestion bancaire basé sur Spring Boot qui fournit des API REST pour gérer les clients bancaires, les comptes, les transactions et les administrateurs.

## Description

BankODC est un projet de démonstration construit avec Spring Boot qui démontre une architecture complète de système bancaire. Il inclut la gestion des utilisateurs (clients et administrateurs), la gestion des comptes et le traitement des transactions avec une base de données PostgreSQL en backend.

## Fonctionnalités

- **Gestion des Utilisateurs** : Support pour les clients et administrateurs avec accès basé sur les rôles
- **Gestion des Comptes** : Créer et gérer des comptes bancaires avec suivi du solde
- **Traitement des Transactions** : Enregistrer et gérer les transactions financières
- **API RESTful** : API REST complète pour toutes les opérations bancaires
- **Intégration Base de Données** : PostgreSQL avec JPA/Hibernate ORM
- **Alimentation de Données** : Population initiale de données pour les tests

## Technologies Utilisées

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **Spring Web**
- **Base de Données PostgreSQL**
- **Lombok** (pour réduire le code boilerplate)
- **Maven** (outil de build)
- **SpringDoc OpenAPI** (documentation API Swagger)

## Prérequis

Avant d'exécuter cette application, assurez-vous d'avoir installé les éléments suivants :

- Java 17 ou supérieur
- Maven 3.6+
- PostgreSQL 12+
- Git

## Installation

1. **Cloner le dépôt :**
   ```bash
   git clone <repository-url>
   cd BankODC
   ```

2. **Installer les dépendances :**
   ```bash
   JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 mvn clean install
   ```

## Configuration

L'application utilise `application.yml` pour la configuration. Paramètres clés :

- **Configuration Base de Données :**
  - URL : `jdbc:postgresql://localhost:5433/Bank`
  - Nom d'utilisateur : `pguser`
  - Mot de passe : `pgpassword`

- **Configuration Serveur :**
  - Port : `8083`

- **Configuration JPA :**
  - DDL Auto : `create-drop`
  - Afficher SQL : `true`

**Note :** Assurez-vous que PostgreSQL fonctionne et que la base de données `Bank` existe avec les identifiants spécifiés.

## Exécution de l'Application

1. **Démarrer le service PostgreSQL** (si pas déjà en cours)

2. **Exécuter l'application :**
   ```bash
   JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 mvn spring-boot:run
   ```

   Ou exécuter le fichier JAR :
   ```bash
   JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 java -jar target/BankODC-0.0.1-SNAPSHOT.jar
   ```

3. **Accéder à l'application :**
   - URL de base API : `http://localhost:8083`
   - Documentation API Swagger : `http://localhost:8083/swagger-ui/index.html`
   - Spécification OpenAPI : `http://localhost:8083/v3/api-docs`

## Documentation API

L'application utilise **Swagger/OpenAPI** pour documenter l'API REST. Une fois l'application démarrée, vous pouvez accéder à :

- **Interface Swagger UI** : `http://localhost:8083/swagger-ui/index.html`
  - Interface interactive pour tester les endpoints
  - Documentation détaillée de chaque endpoint
  - Possibilité d'exécuter des requêtes directement depuis l'interface

- **Spécification OpenAPI JSON** : `http://localhost:8083/v3/api-docs`
  - Spécification complète au format JSON
  - Utilisable pour générer des clients API ou de la documentation

### Fonctionnalités de la documentation :
- Description détaillée de chaque endpoint
- Codes de réponse HTTP documentés
- Schémas des objets de requête/réponse
- Possibilité de tester les API directement depuis l'interface

## Points de Terminaison API

### Clients
- `GET /api/clients` - Obtenir tous les clients
- `GET /api/clients/{id}` - Obtenir un client par ID
- `POST /api/clients` - Créer un nouveau client
- `PUT /api/clients/{id}` - Mettre à jour un client
- `DELETE /api/clients/{id}` - Supprimer un client

### Comptes
- `GET /api/comptes` - Obtenir tous les comptes
- `GET /api/comptes/{id}` - Obtenir un compte par ID
- `POST /api/comptes` - Créer un nouveau compte
- `PUT /api/comptes/{id}` - Mettre à jour un compte
- `DELETE /api/comptes/{id}` - Supprimer un compte

### Transactions
- `GET /api/transactions` - Obtenir toutes les transactions
- `GET /api/transactions/{id}` - Obtenir une transaction par ID
- `POST /api/transactions` - Créer une nouvelle transaction
- `PUT /api/transactions/{id}` - Mettre à jour une transaction
- `DELETE /api/transactions/{id}` - Supprimer une transaction

### Administrateurs
- `GET /api/admins` - Obtenir tous les administrateurs
- `GET /api/admins/{id}` - Obtenir un administrateur par ID
- `POST /api/admins` - Créer un nouvel administrateur
- `PUT /api/admins/{id}` - Mettre à jour un administrateur
- `DELETE /api/admins/{id}` - Supprimer un administrateur

### Utilisateurs
- `GET /api/users` - Obtenir tous les utilisateurs
- `GET /api/users/{id}` - Obtenir un utilisateur par ID
- `POST /api/users` - Créer un nouvel utilisateur
- `PUT /api/users/{id}` - Mettre à jour un utilisateur
- `DELETE /api/users/{id}` - Supprimer un utilisateur

## Schéma de Base de Données

L'application utilise les entités principales suivantes :

- **User** : Entité de base pour l'authentification (ID, nom d'utilisateur, mot de passe)
- **Admin** : Étend User avec des champs spécifiques aux administrateurs
- **Client** : Informations client (détails personnels, lié à User)
- **Compte** : Compte bancaire (numéro de compte, solde, type, lié à User)
- **Transaction** : Transactions financières (montant, type, date, lié à Compte)

## Tests

Exécuter les tests avec Maven :
```bash
JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 mvn test
```

## Contribution

1. Forker le dépôt
2. Créer une branche de fonctionnalité (`git checkout -b feature/AmazingFeature`)
3. Commiter vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Pousser vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## Licence

Ce projet est sous licence MIT - voir le fichier LICENSE pour plus de détails.

## Contact

Pour des questions ou du support, veuillez contacter l'équipe de développement.