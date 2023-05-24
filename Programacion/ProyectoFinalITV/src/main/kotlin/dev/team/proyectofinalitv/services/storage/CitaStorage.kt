package dev.team.proyectofinalitv.services.storage

import com.github.michaelbull.result.Result
import dev.team.proyectofinalitv.errors.CitaError
import dev.team.proyectofinalitv.models.dto.CitaDtoToExport
import java.io.File

interface CitaStorage {
    fun exportToJson(file: File, citaDto: CitaDtoToExport): Result<Boolean, CitaError>
    //fun exportToHtml(file: File, citaDto: CitaDtoToExport): Result<Boolean, CitaError>
    fun exportToHtml(citaDto: CitaDtoToExport)

    // TODO: CRUD imagen
    /*   fun getImagenName(newFileImage: File): String
        fun saveImage(fileName: File): Result<File, Any>
        fun loadImage(fileName: String): Result<File, Any>
        fun deleteImage(fileImage: File): Result<Unit, Any>
        fun updateImage(fileImage: File, newFileImage: File): Result<File, Any>*/
}
