package dev.team.proyectofinalitv.models.dto

import dev.team.proyectofinalitv.models.Trabajador

data class CitaDtoToExport(
    val citaId: Long,
    val estado: String,
    val fechaHora: String, // Cuidado este es el LocalDateTime
    // Empieza trabajador
    val nombreTrabajador: String,
    val correoTrabajador: String,
    val telefonoTrabajador: String,
    val especialidadTrabajador: String,
    val idEstacion: Long,
    // Empieza informe
    val informeId: Long,
    val informeFrenado: Double,
    val informeContaminacion: Double,
    val informeFechaInforme: String,
    val informeInterior: Boolean,
    val informeLuces: Boolean,
    val informeIsApto: Boolean,
    // Empieza Propietario
    val propietarioDni: String,
    val propietarioNombre: String,
    val propietarioApellidos: String,
    val propietarioCorreo: String,
    val propietarioTelefono: String,
    // Empieza Vehiculo
    val vehiculoMatricula: String,
    val vehiculoMarca: String,
    val vehiculoModelo: String,
    val vehiculoFechaMatriculacion: String,
    val vehiculoFechaRevision: String,
    val vehiculoTipoMotor: String,
    val vehiculoTipoVehiculo: String,
)
