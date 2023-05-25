package dev.team.proyectofinalitv.services.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.google.gson.GsonBuilder
import dev.team.proyectofinalitv.errors.CitaError
import dev.team.proyectofinalitv.dto.CitaDtoToExport
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

class CitaStorageImpl : CitaStorage {

    /**
     * Exporta a Json una lista completa de la cita, trabajador responsable, informe, propietario y vehículo
     * @param file la ubicación donde haya elegido nuestro usuario mediante el fileChooser()
     * @param citaDto la cita para exportar
     * @return mediante un Result Unit en caso de ser correcto, CitaError en caso de fallar la exportación
     */
    override fun exportToJson(file: File, citaDto: CitaDtoToExport): Result<Unit, CitaError> {
        logger.debug { "Storage: Exportando a JSON" }

        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonString = gson.toJson(citaDto)
            file.writeText(jsonString)
        } catch (e: Exception) {
            return Err(CitaError.ExportInvalid())
        }

        return Ok(Unit)
    }

    override fun exportToMarkdown(file: File, citaDto: CitaDtoToExport) {
        val apto = "Apto"
        val noApto = "No apto"
        val markdownBuilder = StringBuilder()
        markdownBuilder.apply {
            append("# Informe de cita")
            append("## Datos de cita")
            append("- ID de cita: ${citaDto.citaDto.citaId}")
            append("- Estado de cita: ${citaDto.citaDto.estado}")
            append("- Fecha y hora de cita: ${citaDto.citaDto.fechaHora}")
            append("## Datos del trabajador responsable")
            append("- Nombre del trabajador: ${citaDto.trabajadorDto.nombreTrabajador}")
            append("- Correo del trabajador: ${citaDto.trabajadorDto.correoTrabajador}")
            append("- Teléfono del trabajador: ${citaDto.trabajadorDto.telefonoTrabajador}")
            append("- Especie del trabajador: ${citaDto.trabajadorDto.especialidadTrabajador}")
            append("- ID de estación del trabajador: ${citaDto.trabajadorDto.idEstacion}")
            append("## Informe de inspección técnica")
            append("- ID del informe: ${citaDto.informeDto.informeId}")
            append("- Fecha del informe: ${citaDto.informeDto.informeFechaInforme}")
            append("- Frenado: ${citaDto.informeDto.informeFrenado}")
            append("- Contaminación: ${citaDto.informeDto.informeContaminacion}")
            append("- Interior: ${if (citaDto.informeDto.informeInterior) apto else noApto}")
            append("- Luces: ${if (citaDto.informeDto.informeLuces) apto else noApto}")
            append("### **Resultado del informe:** ${if (citaDto.informeDto.informeIsApto) apto else noApto}")
            append("## Datos del propietario")
            append("- DNI del propietario: ${citaDto.propietarioDto.propietarioDni}")
            append("- Nombre del propietario: ${citaDto.propietarioDto.propietarioNombre} " +
                    citaDto.propietarioDto.propietarioApellidos
            )
            append("- Correo del propietario: ${citaDto.propietarioDto.propietarioCorreo}")
            append("- Teléfono del propietario: ${citaDto.propietarioDto.propietarioTelefono}")
            append("## Datos del vehículo")
            append("- Matrícula del vehículo: ${citaDto.vehiculoDto.vehiculoMatricula}")
            append("- Marca del vehículo: ${citaDto.vehiculoDto.vehiculoMarca}")
            append("- Modelo del vehículo: ${citaDto.vehiculoDto.vehiculoModelo}")
            append("- Fecha de matriculación: ${citaDto.vehiculoDto.vehiculoFechaMatriculacion}")
            append("- Fecha de última revisión: ${citaDto.vehiculoDto.vehiculoFechaRevision}")
            append("- Tipo de motor: ${citaDto.vehiculoDto.vehiculoTipoMotor.lowercase()}")
            append("- Tipo de vehículo: ${citaDto.vehiculoDto.vehiculoTipoVehiculo.lowercase()}")
        }
        val markdown = markdownBuilder.toString()
        file.writeText(markdown)
    }

}
