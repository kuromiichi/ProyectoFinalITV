CREATE DATABASE IF NOT EXISTS ItvDam;

USE ItvDam;

DROP TABLE IF EXISTS Cita, Informe, Vehiculo, Propietario, Trabajador, Estacion;

CREATE TABLE IF NOT EXISTS Estacion
(
    id        INTEGER PRIMARY KEY AUTO_INCREMENT,
    nombre    VARCHAR(50)  NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    correo    VARCHAR(100) NOT NULL,
    telefono  VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS Trabajador
(
    usuario            VARCHAR(50) PRIMARY KEY,
    contrasenya        VARCHAR(50)   NOT NULL,
    nombre             VARCHAR(100)  NOT NULL,
    correo             VARCHAR(100)  NOT NULL,
    telefono           VARCHAR(20)   NOT NULL,
    salario            DECIMAL(6, 2) NOT NULL,
    fecha_contratacion VARCHAR(10)   NOT NULL,
    especialidad       VARCHAR(20)   NOT NULL,
    is_responsable     INTEGER       NOT NULL,
    id_estacion        INT           REFERENCES Estacion (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Propietario
(
    dni       VARCHAR(9) PRIMARY KEY,
    nombre    VARCHAR(50)  NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo    VARCHAR(100) NOT NULL,
    telefono  VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS Vehiculo
(
    matricula           VARCHAR(15) PRIMARY KEY,
    marca               VARCHAR(30) NOT NULL,
    modelo              VARCHAR(50) NOT NULL,
    fecha_matriculacion VARCHAR(10) NOT NULL,
    fecha_revision      VARCHAR(10) NOT NULL,
    tipo_motor          VARCHAR(20) NOT NULL,
    tipo_vehiculo       VARCHAR(20) NOT NULL,
    dni_propietario     VARCHAR(9) REFERENCES Propietario (dni)
);

CREATE TABLE IF NOT EXISTS Informe
(
    id            INTEGER PRIMARY KEY AUTO_INCREMENT,
    frenado       DECIMAL(4, 2) NOT NULL,
    contaminacion DECIMAL(4, 2) NOT NULL,
    fecha_informe VARCHAR(10)   NOT NULL,
    interior      INTEGER       NOT NULL,
    luces         INTEGER       NOT NULL,
    is_apto       INTEGER       NOT NULL
);

CREATE TABLE IF NOT EXISTS Cita
(
    id                 INTEGER PRIMARY KEY AUTO_INCREMENT,
    estado             VARCHAR(10) NOT NULL,
    fecha_hora         VARCHAR(19) NOT NULL,
    id_informe         INTEGER REFERENCES Informe (id),
    usuario_trabajador VARCHAR(50) REFERENCES Trabajador (usuario),
    matricula_vehiculo VARCHAR(15) REFERENCES Vehiculo (matricula)
);
