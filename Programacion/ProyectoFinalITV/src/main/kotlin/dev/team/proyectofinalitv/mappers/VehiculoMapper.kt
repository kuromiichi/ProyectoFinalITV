package dev.team.proyectofinalitv.mappers

import dev.team.proyectofinalitv.models.Vehiculo
import java.util.*

fun parseTipoMotor(tipoMotorString: String): Vehiculo.TipoMotor {
    return when (tipoMotorString.toLowerCase()) {
        "gasolina" -> Vehiculo.TipoMotor.GASOLINA
        "diesel" -> Vehiculo.TipoMotor.DIESEL
        "hibrido" -> Vehiculo.TipoMotor.HIBRIDO
        "electrico" -> Vehiculo.TipoMotor.ELECTRICO
        else -> throw IllegalArgumentException("Tipo de motor inválido: $tipoMotorString")
    }
}

fun parseTipoVehiculo(tipoVehiculoString: String): Vehiculo.TipoVehiculo {
    return when (tipoVehiculoString.toLowerCase()) {
        "turismo" -> Vehiculo.TipoVehiculo.TURISMO
        "furgoneta" -> Vehiculo.TipoVehiculo.FURGONETA
        "camion" -> Vehiculo.TipoVehiculo.CAMION
        "motocicleta" -> Vehiculo.TipoVehiculo.MOTOCICLETA
        else -> throw IllegalArgumentException("Tipo de vehículo inválido: $tipoVehiculoString")
    }
}