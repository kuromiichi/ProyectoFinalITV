-- Ejercicios sobre la base de datos de ITV

-- Crea un procedimiento que liste los inspectores de la estación que recibe como
-- parámetro (la que el propietario ha elegido en la hoja de citas). Hazlo de forma que
-- puedas leer uno a uno y que controles que no se ha llegado al final del fichero.

USE ItvDam;

DROP TEMPORARY TABLE IF EXISTS Inspectores;
CREATE TEMPORARY TABLE Inspectores
(
    usuario VARCHAR(50)
);

DROP PROCEDURE IF EXISTS inspectores_estacion;

DELIMITER $$
CREATE PROCEDURE inspectores_estacion(IN id INTEGER, OUT usuario VARCHAR(50))
BEGIN
    DECLARE fin BOOLEAN DEFAULT FALSE;
    DECLARE id_inspector VARCHAR(50);
    DECLARE cur CURSOR FOR SELECT usuario FROM Trabajador WHERE id_estacion = id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin = TRUE;
    OPEN cur;
    l:
    LOOP
        FETCH cur INTO id_inspector;
        IF fin THEN
            LEAVE l;
        END IF;
        DELETE FROM Inspectores;
        INSERT INTO Inspectores SELECT usuario FROM Trabajador WHERE usuario = id_inspector;
    END LOOP;
    CLOSE cur;
END $$
DELIMITER ;

-- Llama al procedimiento anterior si es necesario para saber si el inspector de la cita
-- pertenece a esa estación. De ser así y, si no tiene citas ese día y hora, puedes cargar
-- los datos en la tabla INSPECCIÓN. Sino tendrás que mandar un mensaje avisando de
-- que ese inspector no está libre.

DROP PROCEDURE IF EXISTS reservar_cita;

DELIMITER $$
CREATE PROCEDURE reservar_cita(IN dni_prop VARCHAR(9), IN nombre_prop VARCHAR(50), IN apellidos_prop VARCHAR(50),
                               IN correo_prop VARCHAR(100), IN telefono_prop VARCHAR(20), IN matricula_v VARCHAR(15),
                               IN marca_v VARCHAR(30), IN modelo_v VARCHAR(50), IN fecha_matriculacion_v DATE,
                               IN fecha_revision_v DATE, IN tipo_motor_v VARCHAR(20), IN tipo_vehiculo_v VARCHAR(20),
                               IN usuario_insp VARCHAR(50), IN id_estacion INT, IN fecha DATETIME)
BEGIN
    DECLARE usuario_inspector VARCHAR(50) := (SELECT usuario
                                              FROM Trabajador
                                              WHERE usuario = usuario_insp);
    CALL inspectores_estacion(id_estacion);
    IF usuario_inspector IN (SELECT usuario FROM Inspectores) THEN
        IF (SELECT COUNT(*) FROM Cita WHERE fecha_hora = fecha) < 8 THEN
            IF (SELECT COUNT(*) FROM Cita WHERE fecha_hora = fecha AND usuario_trabajador = usuario_inspector) < 4 THEN
                INSERT INTO Propietario (dni, nombre, apellidos, correo, telefono)
                VALUES (dni_prop, nombre_prop, apellidos_prop, correo_prop,
                        telefono_prop);
                INSERT INTO Vehiculo (matricula, marca, modelo, fecha_matriculacion, fecha_revision, tipo_motor,
                                      tipo_vehiculo, dni_propietario)
                VALUES (matricula_v, marca_v, modelo_v, fecha_matriculacion_v, fecha_revision_v, tipo_motor_v,
                        tipo_vehiculo_v, dni_prop);
                INSERT INTO Cita (estado, fecha_hora, id_informe, usuario_trabajador, matricula_vehiculo)
                VALUES ('Pendiente', fecha, NULL, usuario_inspector, matricula_v);
            ELSE
                SELECT 'El inspector seleccionado no está disponible en la franja horaria seleccionada' AS message;
            END IF;
        ELSE
            SELECT 'La franja horaria seleccionada está completa' AS message;
        END IF
        $$
    end if
    $$
END $$
DELIMITER ;

-- Controla, mediante un disparador, si se actualiza alguna inspección guardando la
-- información previa y la información que se ha modificado.

DELIMITER $$
CREATE TRIGGER actualizar_inspeccion
    BEFORE UPDATE
    ON Cita
    FOR EACH ROW
BEGIN
    CREATE TEMPORARY TABLE IF NOT EXISTS HistoricoCitas
    (
        id                 INTEGER AUTO_INCREMENT PRIMARY KEY,
        estado             VARCHAR(20),
        fecha_hora         VARCHAR(19),
        id_informe         INTEGER,
        usuario_trabajador VARCHAR(50),
        matricula_vehiculo VARCHAR(15)
    );
    INSERT INTO HistoricoCitas (estado, fecha_hora, id_informe, usuario_trabajador, matricula_vehiculo);
END $$
DELIMITER ;
