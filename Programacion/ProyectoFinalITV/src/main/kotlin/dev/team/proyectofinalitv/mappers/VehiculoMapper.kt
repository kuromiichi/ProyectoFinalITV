package dev.team.proyectofinalitv.mappers

import dev.team.proyectofinalitv.models.Trabajador
import dev.team.proyectofinalitv.models.Vehiculo


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
    return when (tipoMotorString.uppercase()) {
        "GASOLINA" -> Vehiculo.TipoMotor.GASOLINA
        "DIESEL" -> Vehiculo.TipoMotor.DIESEL
        "HIBRIDO" -> Vehiculo.TipoMotor.HIBRIDO
        "ELECTRICO" -> Vehiculo.TipoMotor.ELECTRICO
        else -> throw IllegalArgumentException("Tipo de motor inválido: $tipoMotorString")
    }
}

fun parseTipoVehiculo(tipoVehiculoString: String): Vehiculo.TipoVehiculo {
    return when (tipoVehiculoString.uppercase()) {
        "COCHE" -> Vehiculo.TipoVehiculo.COCHE
        "FURGONETA" -> Vehiculo.TipoVehiculo.FURGONETA
        "CAMION" -> Vehiculo.TipoVehiculo.CAMION
        "MOTOCICLETA" -> Vehiculo.TipoVehiculo.MOTOCICLETA
        "ALL" -> Vehiculo.TipoVehiculo.ALL
        else -> throw IllegalArgumentException("Tipo de vehículo inválido: $tipoVehiculoString")
    }
}
