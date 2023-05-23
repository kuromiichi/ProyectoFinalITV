package dev.team.proyectofinalitv.viewmodels

import dev.team.proyectofinalitv.mappers.parseTipoMotor
import dev.team.proyectofinalitv.mappers.parseTipoVehiculo
import dev.team.proyectofinalitv.models.Cita
import dev.team.proyectofinalitv.models.Informe
import dev.team.proyectofinalitv.models.Propietario
import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.repositories.CitaRepository
import dev.team.proyectofinalitv.repositories.InformeRepository
import dev.team.proyectofinalitv.repositories.PropietarioRepository
import dev.team.proyectofinalitv.repositories.VehiculoRepository
import java.time.LocalDate
import java.time.LocalDateTime

class CitaViewModel(
    private val propietarioRepository: PropietarioRepository,
    private val vehiculoRepository: VehiculoRepository,
    private val informeRepository: InformeRepository,
    private val citaRepository: CitaRepository,
) {

    fun saveCita() {
        // ============== Para guardar una cita, DEBEMOS EN ESTE ORDEN: =============
        // 1. Propietario
        // Debemos crear trigger para que no nos deje crear un nuevo propietario con esta dni!
        val propietario = Propietario(
            dni = "12345678C",
            nombre = "Juan",
            apellidos = "Pérez",
            correo = "juan@example.com",
            telefono = "123456789"
        )
        propietarioRepository.save(propietario)
        // 2. Vehiculo, arrastramos la clave del propietario DNI
        // Debemos crear trigger para que no nos deje crear un nuevo vehiculo con esta matricula!
        val vehiculo = Vehiculo(
            matricula = "123ABCD",
            marca = "Toyota",
            modelo = "Corolla",
            fechaMatriculacion = LocalDate.now(),
            fechaRevision = LocalDate.now(),
            tipoMotor = parseTipoMotor("Gasolina"),
            tipoVehiculo = parseTipoVehiculo("Camion"),
            dniPropietario = propietario.dni
        )
        vehiculoRepository.save(vehiculo)
        // 3. Informe
        // El informe se genera nuevo si todo es correcto
        val informe = Informe(
            id = 1,
            frenado = 4.5,
            contaminacion = 3.2,
            fechaInforme = LocalDate.now(),
            interior = true,
            luces = true,
            isApto = false
        )
        informeRepository.save(informe)
        // EL USER NO HACE FALTA PORQUE SELECCIONAMOS DE LOS QUE TENGAMOS
        // Arrastramos la clave de la matricula y el id del informe, a la cita, si todo es correcto se genera
        val cita = Cita(
            id = 1,
            estado = "No apto",
            fecha_hora = LocalDateTime.now(),
            id_informe = informe.id,
            usuario_trabajador = "p_martinez",
            matricula_vehiculo = vehiculo.matricula
        )
        println(citaRepository.save(cita))
        // ================================================
    }
}
