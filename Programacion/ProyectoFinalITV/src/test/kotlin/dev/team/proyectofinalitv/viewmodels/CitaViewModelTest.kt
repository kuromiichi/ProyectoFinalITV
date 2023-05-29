package dev.team.proyectofinalitv.viewmodels

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.team.proyectofinalitv.dto.*
import dev.team.proyectofinalitv.models.*
import dev.team.proyectofinalitv.repositories.CitaRepository
import dev.team.proyectofinalitv.repositories.base.CRURepository
import dev.team.proyectofinalitv.services.storage.CitaStorage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime

class CitaViewModelTest {

    private val propietarioRepositoryMock: CRURepository<Propietario, String> = mock()
    private val vehiculoRepositoryMock: CRURepository<Vehiculo, String> = mock()
    private val informeRepositoryMock: CRURepository<Informe, Long> = mock()
    private val trabajadorRepositoryMock: CRURepository<Trabajador, String> = mock()
    private val citaRepositoryMock: CitaRepository = mock()
    private val storageMock: CitaStorage = mock()

    private val viewModel: CitaViewModel = CitaViewModel(
        propietarioRepositoryMock,
        vehiculoRepositoryMock,
        informeRepositoryMock,
        trabajadorRepositoryMock,
        citaRepositoryMock,
        storageMock
    )

    val listCitaTest = mutableListOf<Cita>()
    val listTrabajadoresTest = mutableListOf<Trabajador>()
    var listVehiculosTest = mutableListOf<Vehiculo>()
    val listPropietariosTest = mutableListOf<Propietario>()
    val listInformesTest = mutableListOf<Informe>()

    @BeforeEach
    fun setUp() {
        // Cita
        listCitaTest.add(Cita(1, "Apto", LocalDateTime.now(), -1, "j_sanchez", "ABC"))
        listCitaTest.add(Cita(2, "Apto", LocalDateTime.now(), -1, "j_sanchez", "CBA"))

        // Trabajadores
        listTrabajadoresTest.add(Trabajador(
            "a_maroto",
            "jU5#r3s3g",
            "Juan Pérez",
            "juan.perez@itvdam.org",
            "+34912345678",
            1650.0,
            LocalDate.now(),
            Trabajador.Especialidad.ADMINISTRACION,
            false,
            1
        ))

        // Vehiculos
        listVehiculosTest.add(Vehiculo(
            "ABC",
            "a",
            "a",
            LocalDate.of(2023, 5, 1),
            LocalDate.of(2023, 5, 1),
            Vehiculo.TipoMotor.DIESEL,
            Vehiculo.TipoVehiculo.COCHE,
            "ABCD"
        ))
        listVehiculosTest.add(Vehiculo(
            "CBA",
            "a",
            "a",
            LocalDate.now(),
            LocalDate.now(),
            Vehiculo.TipoMotor.DIESEL,
            Vehiculo.TipoVehiculo.COCHE,
            "ABCD"
        ))

        // Informes
        listInformesTest.add( Informe(55, 1.2, 1.2, true, true, true))

        // Propietarios
        listPropietariosTest.add(Propietario(
            dni = "ABCD", nombre = "Juan", apellidos = "Pérez", correo = "juan@example.com", telefono = "123456789"
        ))
    }

    @Test
    fun testGetListCitaFiltered() {
        // Datos de prueba de los filtros que querremos buscar
/*        val tipoVehiculoParaBuscar = Vehiculo.TipoVehiculo.COCHE
        val matriculaParaBuscar = "ABC"
        val fechaParaBuscar = LocalDate.of(2023, 5, 1)

        // Mock de la lista de vehículos
        whenever(vehiculoRepositoryMock.findAll()).thenReturn(listVehiculosTest)

        // Estado de prueba y agregamos al State las cita:
        viewModel.state.value = viewModel.state.value.copy(citas = listCitaTest)

        // Llamada a la función de prueba
        val result = viewModel.getListCitaFiltered(tipoVehiculoParaBuscar, matriculaParaBuscar, fechaParaBuscar)

        // Establezco el estado actual con la lista de citas
        viewModel.state.value = viewModel.state.value.copy(citas = listCitaTest)

        assertEquals(1, result.size)
        assertEquals(listVehiculosTest[0].matricula, result[0]!!.matriculaVehiculo)*/
    }

    @Test
    fun updateCitaSeleccionada() {
        // Crear una cita de ejemplo
        val cita = Cita(1, "Apto", LocalDateTime.now(), 55, "a_maroto", "ABC")

        // Crear trabajador, vehiculo, informe y propietario de ejemplo
        val trabajador = listTrabajadoresTest[0]
        val vehiculo = listVehiculosTest[0]
        val informe = listInformesTest[0]
        val propietario = listPropietariosTest[0]

        // Mockear los métodos de los repositorios para devolver los objetos de ejemplo
        whenever(trabajadorRepositoryMock.findById(cita.usuarioTrabajador)).thenReturn(trabajador)
        whenever(vehiculoRepositoryMock.findById(cita.matriculaVehiculo)).thenReturn(vehiculo)
        whenever(informeRepositoryMock.findById(cita.idInforme)).thenReturn(informe)
        whenever(propietarioRepositoryMock.findById(vehiculo.dniPropietario)).thenReturn(propietario)

        // Llamar al método que se va a probar
        viewModel.updateCitaSeleccionada(cita)

        val citaFormularioPrueba = CitaViewModel.CitaFormulario(
            idCita = cita.id.toString(),
            estadoCita = cita.estado,
            fechaCita = cita.fechaHora.toLocalDate(),
            horaCita = cita.fechaHora.toLocalTime(),
            nombreTrabajador = trabajador.nombre,
            correoTrabajador = trabajador.correo,
            telefonoTrabajador = trabajador.telefono,
            especialidadTrabajador = trabajador.especialidad.name,
            dniPropietario = propietario.dni,
            nombrePropietario = propietario.nombre,
            apellidosPropietario = propietario.apellidos,
            correoPropietario = propietario.correo,
            telefonoPropietario = propietario.telefono,
            matriculaVehiculo = vehiculo.matricula,
            marcaVehiculo = vehiculo.marca,
            modeloVehiculo = vehiculo.modelo,
            fechaMatriculacionVehiculo = vehiculo.fechaMatriculacion,
            fechaRevisionVehiculo = vehiculo.fechaRevision,
            tipoMotorVehiculo = vehiculo.tipoMotor.name.lowercase(),
            tipoVehiculo = vehiculo.tipoVehiculo.name.lowercase(),
            frenadoInforme = informe.frenado,
            contaminacionInforme = informe.contaminacion,
            interiorInforme = informe.interior,
            lucesInforme = informe.luces,
            isAptoInforme = informe.isApto
        )

        // Verificar que se haya actualizado correctamente el estado del ViewModel
        assertEquals(citaFormularioPrueba.idCita, viewModel.state.value.citaSeleccionada.idCita)
    }

    @Test
    fun mapperCitaDtoToExportDelEstado() {
        val citaDb = listCitaTest[0]

        val trabajador = listTrabajadoresTest[0]
        val vehiculo = listVehiculosTest[0]
        val informe = listInformesTest[0]
        val propietario = listPropietariosTest[0]

        // Mockear los métodos de los repositorios para devolver los objetos de ejemplo
        whenever(trabajadorRepositoryMock.findById(citaDb.usuarioTrabajador)).thenReturn(trabajador)
        whenever(vehiculoRepositoryMock.findById(citaDb.matriculaVehiculo)).thenReturn(vehiculo)
        whenever(informeRepositoryMock.findById(citaDb.idInforme)).thenReturn(informe)
        whenever(propietarioRepositoryMock.findById(vehiculo.dniPropietario)).thenReturn(propietario)

        // Ejecutar el método que se va a probar
        val result = viewModel.mapperCitaToCitaDtoToExport(citaDb)

        assertTrue(result.citaDto.citaId == citaDb.id)
    }

    @Test
    fun mapperListCitaDtoToExport() {
        // Configurar el comportamiento del repositorio citaRepositoryMock
        whenever(citaRepositoryMock.findAll()).thenReturn(listCitaTest)

        // Configurar el comportamiento de los repositorios relacionados
        val trabajador = listTrabajadoresTest[0]
        val vehiculo = listVehiculosTest[0]
        val informe = listInformesTest[0]
        val propietario = listPropietariosTest[0]

        whenever(trabajadorRepositoryMock.findById(any())).thenReturn(trabajador)
        whenever(vehiculoRepositoryMock.findById(any())).thenReturn(vehiculo)
        whenever(informeRepositoryMock.findById(any())).thenReturn(informe)
        whenever(propietarioRepositoryMock.findById(any())).thenReturn(propietario)

        // Ejecutar el método que se va a probar
        val result = viewModel.mapperListCitaDtoToExport()

        // Verificar los resultados
        assertEquals(listCitaTest.size, result.size)
    }


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
        val file =  File.createTempFile("cita", ".json") // Archivo temporal

        // Configurar el comportamiento del almacenamiento storageMock
        whenever(storageMock.exportToJson(file, citaDtoToExport)).thenReturn(Ok(Unit))

        // Ejecutar el método que se va a probar
        val result = viewModel.exportToJson(file, citaDtoToExport)

        // Verificar los resultados
        assertTrue(Ok(Unit) == result)
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

        // Configurar el comportamiento del almacenamiento storageMock
        whenever(storageMock.exportAllToJson(file, listCitas)).thenReturn(Ok(Unit))

        // Ejecutar el método que se va a probar
        val result = viewModel.exportAllToJson(file, listCitas)

        // Verificar los resultados
        assertTrue(Ok(Unit) == result)
    }

    @Test
    fun exportToMarkDown() {
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

        val file = File.createTempFile("cita", ".md") // Archivo temporal

        // Configurar el comportamiento del almacenamiento storageMock
        whenever(storageMock.exportToMarkdown(file, citaDtoToExport)).thenReturn(Ok(Unit))

        // Ejecutar el método que se va a probar
        val result = viewModel.exportToMarkDown(file, citaDtoToExport)

        // Verificar los resultados
        assertTrue(Ok(Unit) == result)
    }
}
