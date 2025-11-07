# Étape 1 : Utiliser une image officielle OpenJDK 17 (plus légère et sécurisée)
FROM openjdk:17-slim

# Étape 2 : Définir le répertoire de travail à l'intérieur du conteneur
WORKDIR /app

# Étape 3 : Copier le JAR généré depuis le dossier target de ton projet
COPY target/BankODC-0.0.1-SNAPSHOT.jar app.jar

# Étape 4 : Exposer le port sur lequel ton application Spring Boot écoute
EXPOSE 8080

# Étape 5 : Lancer l’application au démarrage du conteneur
ENTRYPOINT ["java", "-jar", "app.jar"]
