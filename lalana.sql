-- Base de données Lalana pour Oracle
-- Tables pour le projet PathProject

-- Suppression des tables si elles existent (ordre inversé pour les contraintes)
DROP TABLE Route_Point CASCADE CONSTRAINTS;
DROP TABLE Route_Path CASCADE CONSTRAINTS;
DROP TABLE Route CASCADE CONSTRAINTS;
DROP TABLE Hole CASCADE CONSTRAINTS;
DROP TABLE Path CASCADE CONSTRAINTS;
DROP TABLE Point CASCADE CONSTRAINTS;

-- Table Point : représente un point géographique
CREATE TABLE Point (
    id NUMBER PRIMARY KEY,
    x NUMBER NOT NULL,
    y NUMBER NOT NULL,
    nom VARCHAR2(100) NOT NULL
);

-- Table Path : représente un chemin entre deux points
CREATE TABLE Path (
    id NUMBER PRIMARY KEY,
    nom VARCHAR2(100) NOT NULL,
    distance NUMBER NOT NULL,
    largeur NUMBER NOT NULL,
    point_dep_id NUMBER NOT NULL,
    point_arr_id NUMBER NOT NULL,
    CONSTRAINT fk_path_point_dep FOREIGN KEY (point_dep_id) REFERENCES Point(id) ON DELETE CASCADE,
    CONSTRAINT fk_path_point_arr FOREIGN KEY (point_arr_id) REFERENCES Point(id) ON DELETE CASCADE
);

-- Table Hole : représente un trou sur un chemin
CREATE TABLE Hole (
    id NUMBER PRIMARY KEY,
    id_path NUMBER NOT NULL,
    percent NUMBER NOT NULL,
    km_age NUMBER NOT NULL,
    CONSTRAINT fk_hole_path FOREIGN KEY (id_path) REFERENCES Path(id) ON DELETE CASCADE
);

-- Table Route : représente un itinéraire complet
CREATE TABLE Route (
    id NUMBER PRIMARY KEY,
    distance NUMBER NOT NULL,
    duration NUMBER NOT NULL,
    start_point_id NUMBER NOT NULL,
    end_point_id NUMBER NOT NULL,
    CONSTRAINT fk_route_start FOREIGN KEY (start_point_id) REFERENCES Point(id) ON DELETE CASCADE,
    CONSTRAINT fk_route_end FOREIGN KEY (end_point_id) REFERENCES Point(id) ON DELETE CASCADE
);

-- Table de liaison Route_Path : relation many-to-many entre Route et Path
CREATE TABLE Route_Path (
    route_id NUMBER NOT NULL,
    path_id NUMBER NOT NULL,
    ordre NUMBER NOT NULL,
    CONSTRAINT pk_route_path PRIMARY KEY (route_id, path_id),
    CONSTRAINT fk_rp_route FOREIGN KEY (route_id) REFERENCES Route(id) ON DELETE CASCADE,
    CONSTRAINT fk_rp_path FOREIGN KEY (path_id) REFERENCES Path(id) ON DELETE CASCADE
);

-- Table de liaison Route_Point : relation many-to-many entre Route et Point
CREATE TABLE Route_Point (
    route_id NUMBER NOT NULL,
    point_id NUMBER NOT NULL,
    ordre NUMBER NOT NULL,
    CONSTRAINT pk_route_point PRIMARY KEY (route_id, point_id, ordre),
    CONSTRAINT fk_rpt_route FOREIGN KEY (route_id) REFERENCES Route(id) ON DELETE CASCADE,
    CONSTRAINT fk_rpt_point FOREIGN KEY (point_id) REFERENCES Point(id) ON DELETE CASCADE
);

-- Données de test

-- Insertion des Points
INSERT INTO Point (id, x, y, nom) VALUES (1, 10.5, 20.3, 'Point A');
INSERT INTO Point (id, x, y, nom) VALUES (2, 15.2, 25.8, 'Point B');
INSERT INTO Point (id, x, y, nom) VALUES (3, 8.7, 12.1, 'Point C');
INSERT INTO Point (id, x, y, nom) VALUES (4, 5.0, 15.0, 'Point D');
INSERT INTO Point (id, x, y, nom) VALUES (5, 20.5, 30.2, 'Point E');

-- Insertion des Paths
INSERT INTO Path (id, nom, distance, largeur, point_dep_id, point_arr_id) VALUES (1, 'Path AB', 12.5, 2.4, 1, 2);
INSERT INTO Path (id, nom, distance, largeur, point_dep_id, point_arr_id) VALUES (2, 'Path BC', 7.8, 1.8, 2, 3);
INSERT INTO Path (id, nom, distance, largeur, point_dep_id, point_arr_id) VALUES (3, 'Path AC', 20.0, 3.0, 1, 3);
INSERT INTO Path (id, nom, distance, largeur, point_dep_id, point_arr_id) VALUES (4, 'Path DE', 15.3, 2.5, 4, 5);
INSERT INTO Path (id, nom, distance, largeur, point_dep_id, point_arr_id) VALUES (5, 'Path BE', 18.6, 2.2, 2, 5);

-- Insertion des Holes
INSERT INTO Hole (id, id_path, percent, km_age) VALUES (1, 1, 15.5, 2.3);
INSERT INTO Hole (id, id_path, percent, km_age) VALUES (2, 2, 22.0, 1.5);
INSERT INTO Hole (id, id_path, percent, km_age) VALUES (3, 1, 8.5, 3.2);
INSERT INTO Hole (id, id_path, percent, km_age) VALUES (4, 3, 30.0, 4.5);
INSERT INTO Hole (id, id_path, percent, km_age) VALUES (5, 4, 12.0, 1.8);

-- Insertion des Routes
INSERT INTO Route (id, distance, duration, start_point_id, end_point_id) VALUES (1, 25.5, 30.0, 1, 2);
INSERT INTO Route (id, distance, duration, start_point_id, end_point_id) VALUES (2, 18.3, 22.5, 2, 3);
INSERT INTO Route (id, distance, duration, start_point_id, end_point_id) VALUES (3, 32.1, 40.0, 1, 3);
INSERT INTO Route (id, distance, duration, start_point_id, end_point_id) VALUES (4, 45.8, 55.0, 1, 5);
INSERT INTO Route (id, distance, duration, start_point_id, end_point_id) VALUES (5, 28.9, 35.0, 4, 5);

-- Insertion des associations Route_Path
INSERT INTO Route_Path (route_id, path_id, ordre) VALUES (1, 1, 1);
INSERT INTO Route_Path (route_id, path_id, ordre) VALUES (2, 2, 1);
INSERT INTO Route_Path (route_id, path_id, ordre) VALUES (3, 1, 1);
INSERT INTO Route_Path (route_id, path_id, ordre) VALUES (3, 3, 2);
INSERT INTO Route_Path (route_id, path_id, ordre) VALUES (4, 1, 1);
INSERT INTO Route_Path (route_id, path_id, ordre) VALUES (4, 5, 2);
INSERT INTO Route_Path (route_id, path_id, ordre) VALUES (5, 4, 1);

-- Insertion des associations Route_Point
INSERT INTO Route_Point (route_id, point_id, ordre) VALUES (1, 1, 1);
INSERT INTO Route_Point (route_id, point_id, ordre) VALUES (1, 2, 2);
INSERT INTO Route_Point (route_id, point_id, ordre) VALUES (2, 2, 1);
INSERT INTO Route_Point (route_id, point_id, ordre) VALUES (2, 3, 2);
INSERT INTO Route_Point (route_id, point_id, ordre) VALUES (3, 1, 1);
INSERT INTO Route_Point (route_id, point_id, ordre) VALUES (3, 4, 2);
INSERT INTO Route_Point (route_id, point_id, ordre) VALUES (3, 3, 3);
INSERT INTO Route_Point (route_id, point_id, ordre) VALUES (4, 1, 1);
INSERT INTO Route_Point (route_id, point_id, ordre) VALUES (4, 2, 2);
INSERT INTO Route_Point (route_id, point_id, ordre) VALUES (4, 5, 3);
INSERT INTO Route_Point (route_id, point_id, ordre) VALUES (5, 4, 1);
INSERT INTO Route_Point (route_id, point_id, ordre) VALUES (5, 5, 2);

-- Validation des insertions
COMMIT;
