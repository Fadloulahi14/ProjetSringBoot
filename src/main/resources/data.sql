-- Initialisation des données de test

-- Création d'un admin
INSERT INTO users (id, nom, prenom, email, password, telephone, role, user_type) VALUES
('550e8400-e29b-41d4-a716-446655440000', 'Admin', 'System', 'admin@bankodc.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '+221771234567', 'ADMIN', 'ADMIN');

-- Création d'un client et son compte
INSERT INTO users (id, nom, prenom, email, password, telephone, role, user_type) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'Amadou', 'Diallo', 'amadou.diallo@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '+221771234568', 'USER', 'CLIENT');

INSERT INTO clients (id, user_id, nom, prenom, email, telephone, adresse) VALUES
('550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', 'Amadou', 'Diallo', 'amadou.diallo@example.com', '+221771234568', 'Dakar, Sénégal');

INSERT INTO comptes (id, numero_compte, titulaire, type, solde, devise, date_creation, statut, userid) VALUES
('550e8400-e29b-41d4-a716-446655440002', 'C00123456', 'Amadou Diallo', 'epargne', 1250000, 'FCFA', CURRENT_TIMESTAMP, 'ACTIF', '550e8400-e29b-41d4-a716-446655440001');

-- Création d'un deuxième client et son compte
INSERT INTO users (id, nom, prenom, email, password, telephone, role, user_type) VALUES
('550e8400-e29b-41d4-a716-446655440003', 'Hawa', 'BB Wane', 'cheikh.sy@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '+221771234569', 'USER', 'CLIENT');

INSERT INTO clients (id, user_id, nom, prenom, email, telephone, adresse) VALUES
('550e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440003', 'Hawa', 'BB Wane', 'cheikh.sy@example.com', '+221771234569', 'Dakar, Sénégal');

INSERT INTO comptes (id, numero_compte, titulaire, type, solde, devise, date_creation, statut, userid) VALUES
('550e8400-e29b-41d4-a716-446655440004', 'C00123457', 'Hawa BB Wane', 'chèque', 500000, 'FCFA', CURRENT_TIMESTAMP, 'ACTIF', '550e8400-e29b-41d4-a716-446655440003');

-- Insertion des métadonnées pour les comptes
INSERT INTO compte_metadonnees (compte_id, cle, valeur) VALUES
('550e8400-e29b-41d4-a716-446655440002', 'derniereModification', '2023-11-06T10:00:00'),
('550e8400-e29b-41d4-a716-446655440002', 'version', '1'),
('550e8400-e29b-41d4-a716-446655440004', 'derniereModification', '2023-11-06T10:00:00'),
('550e8400-e29b-41d4-a716-446655440004', 'version', '1');