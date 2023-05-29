package dev.team.proyectofinalitv.services.storage

import com.github.michaelbull.result.Ok
import dev.team.proyectofinalitv.dto.*
import dev.team.proyectofinalitv.errors.CitaError
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class CitaStorageImplTest {

    private val storage = CitaStorageImpl()

    @Test
    fun exportToJson() {
        // Datos de prueba para CitaDto
        val citaDto = CitaDto(
            citaId = 1,
            estado = "Pendiente",
            fechaHora = "2023-05-27 10:00:00"
        )

        // Datos de prueba para TrabajadorDto
        val trabajadorDto = TrabajadorDto(
            nombreTrabajador = "John Doe",
            correoTrabajador = "johndoe@example.com",
            telefonoTrabajador = "123456789",
            especialidadTrabajador = "Mecánica",
            idEstacion = 1
        )

        // Datos de prueba para InformeDto
        val informeDto = InformeDto(
            informeId = 1,
            informeFrenado = 0.0,
            informeContaminacion = 0.0,
            informeInterior = true,
            informeLuces = true,
            informeIsApto = true
        )

        // Datos de prueba para PropietarioDto
        val propietarioDto = PropietarioDto(
            propietarioDni = "12345678A",
            propietarioNombre = "Jane Smith",
            propietarioApellidos = "Smith",
            propietarioCorreo = "janesmith@example.com",
            propietarioTelefono = "987654321"
        )

        // Datos de prueba para VehiculoDto
        val vehiculoDto = VehiculoDto(
            vehiculoMatricula = "ABC123",
            vehiculoMarca = "Toyota",
            vehiculoModelo = "Corolla",
            vehiculoFechaMatriculacion = "2020-01-01",
            vehiculoFechaRevision = "2023-05-01",
            vehiculoTipoMotor = "Gasolina",
            vehiculoTipoVehiculo = "Automóvil"
        )

        // Crear instancia de CitaDtoToExport con los datos de prueba
        val citaDtoToExport = CitaDtoToExport(
            citaDto = citaDto,
            trabajadorDto = trabajadorDto,
            informeDto = informeDto,
            propietarioDto = propietarioDto,
            vehiculoDto = vehiculoDto
        )

        val file = File.createTempFile("cita", ".json")

        // Llamar a la función de prueba
        val result = storage.exportToJson(file,citaDtoToExport)

        // Verificar el resultado
        assertTrue(Ok(Unit) == result)
        assertTrue(file.exists())
    }

    @Test
    fun exportAllToJson() {

        // Datos de prueba para CitaDto
        val citaDto = CitaDto(
            citaId = 1,
            estado = "Pendiente",
            fechaHora = "2023-05-27 10:00:00"
        )

        // Datos de prueba para TrabajadorDto
        val trabajadorDto = TrabajadorDto(
            nombreTrabajador = "John Doe",
            correoTrabajador = "johndoe@example.com",
            telefonoTrabajador = "123456789",
            especialidadTrabajador = "Mecánica",
            idEstacion = 1
        )

        // Datos de prueba para InformeDto
        val informeDto = InformeDto(
            informeId = 1,
            informeFrenado = 0.0,
            informeContaminacion = 0.0,
            informeInterior = true,
            informeLuces = true,
            informeIsApto = true
        )

        // Datos de prueba para PropietarioDto
        val propietarioDto = PropietarioDto(
            propietarioDni = "12345678A",
            propietarioNombre = "Jane Smith",
            propietarioApellidos = "Smith",
            propietarioCorreo = "janesmith@example.com",
            propietarioTelefono = "987654321"
        )

        // Datos de prueba para VehiculoDto
        val vehiculoDto = VehiculoDto(
            vehiculoMatricula = "ABC123",
            vehiculoMarca = "Toyota",
            vehiculoModelo = "Corolla",
            vehiculoFechaMatriculacion = "2020-01-01",
            vehiculoFechaRevision = "2023-05-01",
            vehiculoTipoMotor = "Gasolina",
            vehiculoTipoVehiculo = "Automóvil"
        )

        // Crear instancia de CitaDtoToExport con los datos de prueba
        val citaDtoToExport = CitaDtoToExport(
            citaDto = citaDto,
            trabajadorDto = trabajadorDto,
            informeDto = informeDto,
            propietarioDto = propietarioDto,
            vehiculoDto = vehiculoDto
        )

        val file = File.createTempFile("citas", ".json") // Archivo temporal
        val listCitas = listOf(citaDtoToExport) // Lista de citas a exportar

        // Llamar a la función de prueba
        val result = storage.exportAllToJson(file,listCitas)

        // Verificar el resultado
        assertTrue(Ok(Unit) == result)
        assertTrue(file.exists())
    }

    @Test
    fun exportToMarkdown() {

        // Datos de prueba para CitaDto
        val citaDto = CitaDto(
            citaId = 1,
            estado = "Pendiente",
            fechaHora = "2023-05-27 10:00:00"
        )

        // Datos de prueba para TrabajadorDto
        val trabajadorDto = TrabajadorDto(
            nombreTrabajador = "John Doe",
            correoTrabajador = "johndoe@example.com",
            telefonoTrabajador = "123456789",
            especialidadTrabajador = "Mecánica",
            idEstacion = 1
        )

        // Datos de prueba para InformeDto
        val informeDto = InformeDto(
            informeId = 1,
            informeFrenado = 0.0,
            informeContaminacion = 0.0,
            informeInterior = true,
            informeLuces = true,
            informeIsApto = true
        )

        // Datos de prueba para PropietarioDto
        val propietarioDto = PropietarioDto(
            propietarioDni = "12345678A",
            propietarioNombre = "Jane Smith",
            propietarioApellidos = "Smith",
            propietarioCorreo = "janesmith@example.com",
            propietarioTelefono = "987654321"
        )

        // Datos de prueba para VehiculoDto
        val vehiculoDto = VehiculoDto(
            vehiculoMatricula = "ABC123",
            vehiculoMarca = "Toyota",
            vehiculoModelo = "Corolla",
            vehiculoFechaMatriculacion = "2020-01-01",
            vehiculoFechaRevision = "2023-05-01",
            vehiculoTipoMotor = "Gasolina",
            vehiculoTipoVehiculo = "Automóvil"
        )

        // Crear instancia de CitaDtoToExport con los datos de prueba
        val citaDtoToExport = CitaDtoToExport(
            citaDto = citaDto,
            trabajadorDto = trabajadorDto,
            informeDto = informeDto,
            propietarioDto = propietarioDto,
            vehiculoDto = vehiculoDto
        )

        val file = File.createTempFile("cita", ".md")

        // Llamar a la función de prueba
        val result = storage.exportToMarkdown(file ,citaDtoToExport)

        // Verificar el resultado
        assertTrue(Ok(Unit) == result)
        assertTrue(file.exists())
    }
}
