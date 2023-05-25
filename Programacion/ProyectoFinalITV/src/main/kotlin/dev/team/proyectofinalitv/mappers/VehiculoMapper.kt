package dev.team.proyectofinalitv.mappers

import dev.team.proyectofinalitv.models.Trabajador
import dev.team.proyectofinalitv.models.Vehiculo
import java.util.*


fun parseEspecialidad(especialidadString: String): Trabajador.Especialidad {
    return when (especialidadString.uppercase()) {
        "ADMINISTRACION" -> Trabajador.Especialidad.ADMINISTRACION
        "ELECTRICIDAD" -> Trabajador.Especialidad.ELECTRICIDAD
        "MOTOR" -> Trabajador.Especialidad.MOTOR
        "MECANICA" -> Trabajador.Especialidad.MECANICA
        "INTERIOR" -> Trabajador.Especialidad.INTERIOR
        else -> throw IllegalArgumentException("Especialidad inválida: $especialidadString")
    }
}

fun parseTipoMotor(tipoMotorString: String): Vehiculo.TipoMotor {
    return when (tipoMotorString.lowercase()) {
        "gasolina" -> Vehiculo.TipoMotor.GASOLINA
        "diesel" -> Vehiculo.TipoMotor.DIESEL
        "hibrido" -> Vehiculo.TipoMotor.HIBRIDO
        "electrico" -> Vehiculo.TipoMotor.ELECTRICO
        else -> throw IllegalArgumentException("Tipo de motor inválido: $tipoMotorString")
    }
}

fun parseTipoVehiculo(tipoVehiculoString: String): Vehiculo.TipoVehiculo {
    return when (tipoVehiculoString.lowercase()) {
        "coche" -> Vehiculo.TipoVehiculo.COCHE
        "furgoneta" -> Vehiculo.TipoVehiculo.FURGONETA
        "camion" -> Vehiculo.TipoVehiculo.CAMION
        "motocicleta" -> Vehiculo.TipoVehiculo.MOTOCICLETA
        else -> throw IllegalArgumentException("Tipo de vehículo inválido: $tipoVehiculoString")
    }
}
