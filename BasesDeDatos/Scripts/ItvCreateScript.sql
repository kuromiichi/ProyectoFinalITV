CREATE TABLE IF NOT EXISTS Estacion
(
    id        TEXT PRIMARY KEY AUTOINCREMENT,
    nombre    TEXT NOT NULL,
    direccion TEXT NOT NULL,
    correo    TEXT NOT NULL,
    telefono  TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Trabajador
(
    usuario            TEXT PRIMARY KEY,
    contrasenya        TEXT NOT NULL,
    nombre             TEXT NOT NULL,
    correo             TEXT NOT NULL,
    telefono           TEXT NOT NULL,
    salario            REAL NOT NULL,
    fecha_contratacion TEXT NOT NULL,
    especialidad       TEXT NOT NULL,
    id_estacion        INT REFERENCES Estacion (id)
);

CREATE TABLE IF NOT EXISTS Propietario
(
    dni       TEXT PRIMARY KEY,
    nombre    TEXT NOT NULL,
    apellidos TEXT NOT NULL,
    correo    TEXT NOT NULL,
    telefono  TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Vehiculo
(
    matricula           TEXT PRIMARY KEY,
    marca               TEXT NOT NULL,
    modelo              TEXT NOT NULL,
    fecha_matriculacion TEXT NOT NULL,
    fecha_revision      TEXT NOT NULL,
    tipo_motor          TEXT NOT NULL,
    tipo_vehiculo       TEXT NOT NULL,
    dni_propietario     TEXT REFERENCES Propietario (dni)
);

CREATE TABLE IF NOT EXISTS Informe
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    frenado       REAL    NOT NULL,
    contaminacion REAL    NOT NULL,
    fecha_informe TEXT    NOT NULL,
    -- Campos boolean (0 o 1)
    interior      INTEGER NOT NULL,
    luces         INTEGER NOT NULL,
    is_apto       INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS Cita
(
    id                 INTEGER PRIMARY KEY AUTOINCREMENT,
    estado             TEXT NOT NULL,
    fecha              TEXT NOT NULL,
    id_informe         INTEGER REFERENCES Informe (id),
    usuario_trabajador TEXT REFERENCES Trabajador (usuario),
    matricula_vehiculo TEXT REFERENCES Vehiculo (matricula)
);
