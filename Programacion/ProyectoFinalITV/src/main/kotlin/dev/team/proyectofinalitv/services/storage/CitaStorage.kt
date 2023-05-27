package dev.team.proyectofinalitv.services.storage

import com.github.michaelbull.result.Result
import dev.team.proyectofinalitv.errors.CitaError
import dev.team.proyectofinalitv.dto.CitaDtoToExport
import java.io.File

interface CitaStorage {
    fun exportToJson(file: File, citaDto: CitaDtoToExport): Result<Unit, CitaError>

    fun exportAllToJson(file: File, listCitaDto: List<CitaDtoToExport>): Result<Unit, CitaError>

    fun exportToMarkdown(file: File, citaDto: CitaDtoToExport): Result<Unit, CitaError>
}
