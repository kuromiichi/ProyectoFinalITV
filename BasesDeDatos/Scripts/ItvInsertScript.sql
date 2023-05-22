USE ItvDam;

INSERT INTO Estacion (nombre, direccion, correo, telefono)
VALUES ('ITV DAM', 'Paseo de la Ermita 15', 'itvdam@itvdam.org', '+34822659855');

INSERT INTO Trabajador (usuario, contrasenya, nombre, correo, telefono, salario, fecha_contratacion, especialidad,
                        id_estacion)
VALUES ('j_perez', 'jU5#r3s3g', 'Juan Pérez', 'juan.perez@itvdam.org', '+34912345678', 1650.00, '2022-01-15',
        'ADMINISTRACION', 1),
       ('m_lopez', 'M@r14L0p$', 'María López', 'maria.lopez@itvdam.org', '+34911234567', 1800.00, '2021-09-20',
        'ELECTRICIDAD', 1),
       ('d_garcia', 'D4v1dG@rc14', 'David García', 'david.garcia@itvdam.org', '+34910987654', 1700.00, '2023-02-10',
        'MOTOR', 1),
       ('a_rodriguez', 'Alic14R0dr1', 'Alicia Rodríguez', 'alicia.rodriguez@itvdam.org', '+34913456789', 1600.00,
        '2022-11-05', 'MECANICA', 1),
       ('l_hernandez', 'L@ur4H3rn', 'Laura Hernández', 'laura.hernandez@itvdam.org', '+34914567890', 1750.00,
        '2021-12-30', 'INTERIOR', 1),
       ('p_martinez', 'P3dr0M@rt', 'Pedro Martínez', 'pedro.martinez@itvdam.org', '+34915678901', 1650.00, '2023-03-18',
        'ADMINISTRACION', 1),
       ('s_gomez', 'Sof14G0m$', 'Sofía Gómez', 'sofia.gomez@itvdam.org', '+34916789012', 1800.00, '2022-07-22',
        'ELECTRICIDAD', 1),
       ('j_sanchez', 'Ju4nS@nch3z', 'Juan Sánchez', 'juan.sanchez@itvdam.org', '+34917890123', 1700.00, '2022-04-02',
        'MOTOR', 1),
       ('c_romero', 'C4rm3nR0m', 'Carmen Romero', 'carmen.romero@itvdam.org', '+34918901234', 1600.00, '2023-01-12',
        'MECANICA', 1),
       ('r_lopez', 'R0b3rtL0p!', 'Roberto López', 'roberto.lopez@itvdam.org', '+34919012345', 1750.00, '2022-08-27',
        'INTERIOR', 1);

INSERT INTO Propietario (dni, nombre, apellidos, correo, telefono)
VALUES ('86548973P', 'Carlos', 'Gómez', 'carlos.gomez@gmail.com', '+34651234567'),
       ('54892376T', 'Laura', 'Torres', 'laura.torres@gmail.com', '+34659876543'),
       ('33571289M', 'Pablo', 'Hernández', 'pablo.hernandez@gmail.com', '+34647851236'),
       ('75261498N', 'Isabel', 'García', 'isabel.garcia@gmail.com', '+34652148796'),
       ('45789632R', 'Manuel', 'Ruiz', 'manuel.ruiz@gmail.com', '+34658473920'),
       ('93651247L', 'Carmen', 'Morales', 'carmen.morales@gmail.com', '+34657982461'),
       ('28963517S', 'Ricardo', 'Ortega', 'ricardo.ortega@gmail.com', '+34652789413'),
       ('69587412U', 'Marta', 'Sánchez', 'marta.sanchez@gmail.com', '+34651327894'),
       ('12547896X', 'Pedro', 'González', 'pedro.gonzalez@gmail.com', '+34652784596'),
       ('96321478Y', 'Ana', 'López', 'ana.lopez@gmail.com', '+34658963214');

INSERT INTO Vehiculo (matricula, marca, modelo, fecha_matriculacion, fecha_revision, tipo_motor, tipo_vehiculo,
                      dni_propietario)
VALUES ('5794-RGK', 'Ford', 'Focus', '2020-01-15', '2023-01-15', 'GASOLINA', 'Coche', '86548973P'),
       ('1267-BTK', 'Toyota', 'Corolla', '2019-05-20', '2022-05-20', 'ELECTRICO', 'Coche', '54892376T'),
       ('2803-HNS', 'Volkswagen', 'Golf', '2021-08-10', '2024-08-10', 'DIESEL', 'Coche', '33571289M'),
       ('4578-FLS', 'Renault', 'Clio', '2018-11-28', '2021-11-28', 'GASOLINA', 'Coche', '75261498N'),
       ('8026-QJK', 'BMW', 'X5', '2022-02-05', '2025-02-05', 'HIBRIDO', 'SUV', '45789632R'),
       ('2354-DHP', 'Ford', 'Mustang', '2020-03-12', '2023-03-12', 'ELECTRICO', 'Coche', '93651247L'),
       ('6908-RSV', 'Nissan', 'Qashqai', '2019-06-30', '2022-06-30', 'DIESEL', 'SUV', '28963517S'),
       ('1482-CKM', 'Mercedes-Benz', 'A-Class', '2021-09-18', '2024-09-18', 'HIBRIDO', 'Coche', '69587412U'),
       ('3761-PZX', 'Audi', 'A4', '2022-04-25', '2025-04-25', 'GASOLINA', 'Coche', '12547896X'),
       ('5139-TBS', 'Volkswagen', 'Polo', '2021-07-05', '2024-07-05', 'DIESEL', 'Coche', '96321478Y');

INSERT INTO Informe (frenado, contaminacion, fecha_informe, interior, luces, is_apto)
VALUES (8.35, 30.75, '2023-04-20', 1, 1, 0),
       (6.92, 42.18, '2023-04-22', 0, 1, 1),
       (3.76, 25.62, '2023-04-25', 1, 0, 0),
       (9.87, 47.93, '2023-04-28', 0, 1, 1),
       (5.21, 38.79, '2023-05-02', 1, 0, 0),
       (7.64, 31.45, '2023-05-05', 0, 1, 1),
       (2.98, 26.87, '2023-05-08', 1, 0, 0),
       (8.14, 43.26, '2023-05-10', 1, 1, 1),
       (4.57, 28.93, '2023-05-12', 0, 0, 0),
       (9.23, 34.51, '2023-05-15', 1, 1, 1);

INSERT INTO Cita (estado, fecha_hora, id_informe, usuario_trabajador, matricula_vehiculo)
VALUES ('No apto', '2023-04-20 09:00:00', 1, 'j_perez', '5794-RGK'),
       ('Apto', '2023-04-22 13:30:00', 2, 'm_lopez', '1267-BTK'),
       ('No apto', '2023-04-25 10:30:00', 3, 'd_garcia', '2803-HNS'),
       ('Apto', '2023-04-28 15:30:00', 4, 'a_rodriguez', '4578-FLS'),
       ('No apto', '2023-05-02 11:00:00', 5, 'l_hernandez', '8026-QJK'),
       ('Apto', '2023-05-05 14:30:00', 6, 'p_martinez', '2354-DHP'),
       ('No apto', '2023-05-08 09:30:00', 7, 's_gomez', '6908-RSV'),
       ('Apto', '2023-05-10 16:00:00', 8, 'j_sanchez', '1482-CKM'),
       ('No apto', '2023-05-12 12:30:00', 9, 'c_romero', '3761-PZX'),
       ('Apto', '2023-05-15 14:00:00', 10, 'r_lopez', '5139-TBS');
