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
     * Exporta a Json con los datos de una cita, trabajador responsable, informe, propietario y vehículo
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
            return Err(CitaError.ExportInvalidJson())
        }

        return Ok(Unit)
    }

    /**
     * Exporta a Json una lista completa de todas las citas y sus datos
     * @param file la ubicación donde haya elegido nuestro usuario mediante el fileChooser()
     * @param listCitaDto todas las citas
     * @return mediante un Result Unit en caso de ser correcto, CitaError en caso de fallar la exportación
     */
    override fun exportAllToJson(file: File, listCitaDto: List<CitaDtoToExport>): Result<Unit, CitaError> {
        logger.debug { "Storage: Exportando todas las citas a JSON" }

        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonString = gson.toJson(listCitaDto)
            file.writeText(jsonString)
        } catch (e: Exception) {
            return Err(CitaError.ExportInvalidJson())
        }

        return Ok(Unit)
    }

    /**
     * Exporta a HTML/Markdown una lista completa de la cita, trabajador responsable, informe, propietario y vehículo
     * @param file la ubicación donde haya elegido nuestro usuario mediante el fileChooser()
     * @param citaDto la cita para exportar
     * @return mediante un Result Unit en caso de ser correcto, CitaError en caso de fallar la exportación
     */
    override fun exportToMarkdown(file: File, citaDto: CitaDtoToExport): Result<Unit, CitaError> {
        logger.debug { "Storage: Exportando a HTML" }

        try {
            val apto = "Apto"
            val noApto = "No apto"
            val markdownBuilder = StringBuilder()
            markdownBuilder.apply {
                append("# Informe de cita\n")
                append("## Datos de cita\n")
                append("- ID de cita: ${citaDto.citaDto.citaId}\n")
                append("- Estado de cita: ${citaDto.citaDto.estado}\n")
                append("- Fecha y hora de cita: ${citaDto.citaDto.fechaHora}\n")

                append("## Datos del trabajador responsable\n")
                append("- Nombre del trabajador: ${citaDto.trabajadorDto.nombreTrabajador}\n")
                append("- Correo del trabajador: ${citaDto.trabajadorDto.correoTrabajador}\n")
                append("- Teléfono del trabajador: ${citaDto.trabajadorDto.telefonoTrabajador}\n")
                append("- Especie del trabajador: ${citaDto.trabajadorDto.especialidadTrabajador}\n")
                append("- ID de estación del trabajador: ${citaDto.trabajadorDto.idEstacion}\n")

                append("## Informe de inspección técnica\n")
                append("- ID del informe: ${citaDto.informeDto.informeId}\n")
                append("- Frenado: ${citaDto.informeDto.informeFrenado}\n")
                append("- Contaminación: ${citaDto.informeDto.informeContaminacion}\n")
                append("- Interior: ${if (citaDto.informeDto.informeInterior) apto else noApto}\n")
                append("- Luces: ${if (citaDto.informeDto.informeLuces) apto else noApto}\n")

                append("### **Resultado del informe:** ${if (citaDto.informeDto.informeIsApto) apto else noApto}\n")
                append("## Datos del propietario\n")
                append("- DNI del propietario: ${citaDto.propietarioDto.propietarioDni}\n")
                append("- Nombre del propietario: ${citaDto.propietarioDto.propietarioNombre} " +
                        citaDto.propietarioDto.propietarioApellidos + "\n")
                append("- Correo del propietario: ${citaDto.propietarioDto.propietarioCorreo}\n")
                append("- Teléfono del propietario: ${citaDto.propietarioDto.propietarioTelefono}\n")

                append("## Datos del vehículo\n")
                append("- Matrícula del vehículo: ${citaDto.vehiculoDto.vehiculoMatricula}\n")
                append("- Marca del vehículo: ${citaDto.vehiculoDto.vehiculoMarca}\n")
                append("- Modelo del vehículo: ${citaDto.vehiculoDto.vehiculoModelo}\n")
                append("- Fecha de matriculación: ${citaDto.vehiculoDto.vehiculoFechaMatriculacion}\n")
                append("- Fecha de última revisión: ${citaDto.vehiculoDto.vehiculoFechaRevision}\n")
                append("- Tipo de motor: ${citaDto.vehiculoDto.vehiculoTipoMotor.lowercase()}\n")
                append("- Tipo de vehículo: ${citaDto.vehiculoDto.vehiculoTipoVehiculo.lowercase()}\n")
            }
            val markdown = markdownBuilder.toString()
            file.writeText(markdown)
        } catch (e: Exception) {
            return Err(CitaError.ExportInvalidMarkDown())
        }

        return Ok(Unit)
    }
}
