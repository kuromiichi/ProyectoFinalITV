package dev.team.proyectofinalitv.services.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.google.gson.GsonBuilder
import dev.team.proyectofinalitv.errors.CitaError
import dev.team.proyectofinalitv.models.dto.CitaDtoToExport
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

class CitaStorageImpl : CitaStorage {

    /**
     * Exporta a Json una lista completa de la cita, trabajador responsable, informe, propietario y vehículo
     * @param file la ubicación donde haya elegido nuestro usuario mediante el fileChooser()
     * @param citaDto la cita para exportar
     * @return mediante un Result un booleano en caso de ser correcto,en caso de fallar la exportación un ErrorPersonalizado
     */
    override fun exportToJson(file: File, citaDto: CitaDtoToExport): Result<Boolean, CitaError> {
        logger.debug { "Storage: Exportando a JSON" }

        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonString = gson.toJson(citaDto)
            file.writeText(jsonString)
        } catch (e: Exception) {
            return Err(CitaError.ExportInvalid())
        }

        return Ok(true)
    }

    // TODO()
    override fun exportToHtml(citaDto: CitaDtoToExport) {
        val htmlBuilder = StringBuilder()

        htmlBuilder.append("<html>")
        htmlBuilder.append("<head>")
        htmlBuilder.append("</head>")
        htmlBuilder.append("<body>")

        htmlBuilder.append("<div class=\"cita\">")
        htmlBuilder.append("<h3>Cita ID: ${citaDto.citaId}</h3>")
        htmlBuilder.append("<p>Estado: ${citaDto.estado}</p>")
        // Resto de las propiedades...
        htmlBuilder.append("</div>")

        htmlBuilder.append("</body>")
        htmlBuilder.append("</html>")

        val html = htmlBuilder.toString()
    }

}
