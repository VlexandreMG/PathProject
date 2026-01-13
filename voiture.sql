-- Tables pour les voitures et leurs types

-- Table Type : types de véhicules
CREATE TABLE Type (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

-- Table Car (Voiture) : véhicules avec leurs caractéristiques
CREATE TABLE Car (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    vitesse_max DOUBLE PRECISION NOT NULL,
    id_type INTEGER NOT NULL,
    longueur DOUBLE PRECISION NOT NULL,
    largeur DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (id_type) REFERENCES Type(id) ON DELETE CASCADE
);

-- Index pour améliorer les performances
CREATE INDEX idx_car_type ON Car(id_type);

-- Données d'exemple pour Type
INSERT INTO Type (id, nom) VALUES 
    (1, 'Berline'),
    (2, 'SUV'),
    (3, 'Sportive'),
    (4, 'Camionnette'),
    (5, 'Compacte');

-- Données d'exemple pour Car
INSERT INTO Car (nom, vitesse_max, id_type, longueur, largeur) VALUES 
    ('Tesla Model S', 250.0, 3, 4.97, 1.96),
    ('Toyota RAV4', 180.0, 2, 4.60, 1.85),
    ('Renault Clio', 180.0, 5, 4.05, 1.73),
    ('Porsche 911', 300.0, 3, 4.52, 1.85),
    ('Ford Transit', 150.0, 4, 5.34, 2.05);
